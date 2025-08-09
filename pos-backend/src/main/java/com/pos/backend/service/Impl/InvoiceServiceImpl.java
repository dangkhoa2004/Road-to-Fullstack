package com.pos.backend.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.backend.dto.invoice.InvoiceItemResponse;
import com.pos.backend.dto.invoice.InvoiceRequest;
import com.pos.backend.dto.invoice.InvoiceResponse;
import com.pos.backend.dto.payment.PaymentCreationResponse;
import com.pos.backend.dto.payment.PaymentRequest;
import com.pos.backend.dto.payment.PaymentResponse;
import com.pos.backend.model.Invoice;
import com.pos.backend.model.InvoiceItem;
import com.pos.backend.model.Payment;
import com.pos.backend.model.PaymentMethod;
import com.pos.backend.model.Product;
import com.pos.backend.repository.CustomerRepository;
import com.pos.backend.repository.DiscountRepository;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.repository.InvoiceRepository;
import com.pos.backend.repository.PaymentMethodRepository;
import com.pos.backend.repository.ProductRepository;
import com.pos.backend.repository.TablesRepository;
import com.pos.backend.service.base.InvoiceService;

import lombok.RequiredArgsConstructor;
import vn.payos.PayOS;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private static final BigDecimal TAX_RATE = new BigDecimal("0.10");

    private final InvoiceRepository invoiceRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;
    private final EmployeeRepository employeeRepository;
    private final TablesRepository tablesRepository;
    private final DiscountRepository discountRepository;
    private final PaymentMethodRepository paymentMethodRepository;
    private final PayOS payOS;

    @Value("${app.frontend-url:}")
    private String frontendUrl;

    @Override
    @Transactional
    public InvoiceResponse create(InvoiceRequest request) {
        // --- BƯỚC 1: TẠO ĐỐI TƯỢNG INVOICE VÀ LIÊN KẾT CÁC THỰC THỂ ---
        Invoice invoice = new Invoice();
        invoice.setEmployee(employeeRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new NoSuchElementException("Employee not found with ID: " + request.getEmployeeId())));

        if (request.getCustomerId() != null) {
            invoice.setCustomer(customerRepository.findById(request.getCustomerId())
                    .orElseThrow(() -> new NoSuchElementException("Customer not found with ID: " + request.getCustomerId())));
        }
        if (request.getTableId() != null) {
            invoice.setTable(tablesRepository.findById(request.getTableId())
                    .orElseThrow(() -> new NoSuchElementException("Table not found with ID: " + request.getTableId())));
        }
        if (request.getDiscountId() != null) {
            invoice.setDiscount(discountRepository.findById(request.getDiscountId())
                    .orElseThrow(() -> new NoSuchElementException("Discount not found with ID: " + request.getDiscountId())));
        }
        invoice.setNote(request.getNote());
        invoice.setStatus(Invoice.InvoiceStatus.pending);

        // --- BƯỚC 2: XỬ LÝ CÁC MỤC TRONG HÓA ĐƠN VÀ TÍNH TOÁN ---
        BigDecimal subTotal = BigDecimal.ZERO;
        List<InvoiceItem> items = new ArrayList<>();
        for (var itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId())
                    .orElseThrow(() -> new NoSuchElementException("Product not found with ID: " + itemReq.getProductId()));

            int quantityToSell = itemReq.getQuantity();
            if (product.getQuantity() < quantityToSell) {
                throw new IllegalArgumentException("Số lượng tồn kho của sản phẩm '" + product.getName() + "' không đủ. Chỉ còn " + product.getQuantity() + ".");
            }
            product.setQuantity(product.getQuantity() - quantityToSell);

            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(quantityToSell));
            subTotal = subTotal.add(itemTotal);

            InvoiceItem ii = new InvoiceItem();
            ii.setInvoice(invoice);
            ii.setProduct(product);
            ii.setQuantity(quantityToSell);
            ii.setUnitPrice(product.getPrice());
            ii.setItemTotal(itemTotal);
            items.add(ii);
        }
        invoice.setInvoiceItems(items);
        invoice.setSubTotal(subTotal);

        // --- BƯỚC 3: TÍNH TOÁN TỔNG CỘNG ---
        BigDecimal discountAmt = invoice.getDiscount() != null ? invoice.getDiscount().calculate(subTotal) : BigDecimal.ZERO;
        invoice.setDiscountAmount(discountAmt);
        BigDecimal taxAmt = subTotal.subtract(discountAmt).multiply(TAX_RATE);
        invoice.setTaxAmount(taxAmt);
        invoice.setTotalAmount(subTotal.subtract(discountAmt).add(taxAmt));

        // --- BƯỚC 4: LƯU VÀ TRẢ VỀ ---
        Invoice savedInvoice = invoiceRepository.save(invoice);
        return mapToResponse(savedInvoice);
    }

    @Override
    @Transactional
    public PaymentCreationResponse addPaymentToInvoice(Long invoiceId, PaymentRequest paymentReq) {
        // --- BƯỚC 1: TÌM HÓA ĐƠN VÀ KIỂM TRA TRẠNG THÁI ---
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NoSuchElementException("Invoice not found with ID: " + invoiceId));

        if (invoice.getStatus() != Invoice.InvoiceStatus.pending) {
            throw new IllegalStateException("Không thể thêm thanh toán. Hóa đơn đang ở trạng thái: " + invoice.getStatus());
        }

        // --- BƯỚC 2: TẠO ĐỐI TƯỢNG THANH TOÁN ---
        PaymentMethod paymentMethod = paymentMethodRepository.findById(paymentReq.getPaymentMethodId())
                .orElseThrow(() -> new NoSuchElementException("Payment Method not found with ID: " + paymentReq.getPaymentMethodId()));
        
        Payment payment = new Payment();
        payment.setAmount(paymentReq.getAmount());
        payment.setPaymentMethod(paymentMethod);
        payment.setInvoice(invoice);
        payment.setStatus(Payment.PaymentStatus.PENDING);

        String checkoutUrl = null;

        // --- BƯỚC 3: XỬ LÝ LOGIC PAYOS (NẾU CÓ) ---
        if ("PayOS".equalsIgnoreCase(paymentMethod.getName())) {
            try {
                PaymentData paymentData = createPayOSData(invoice);
                var paymentLink = payOS.createPaymentLink(paymentData);

                checkoutUrl = paymentLink.getCheckoutUrl();
                payment.setTransactionCode(paymentLink.getPaymentLinkId());
            } catch (Exception e) {
                throw new RuntimeException("Failed to create PayOS payment link: " + e.getMessage(), e);
            }
        }
        
        // --- BƯỚC 4: LƯU THÔNG TIN VÀ TRẢ VỀ ---
        if (invoice.getPayments() == null) {
            invoice.setPayments(new ArrayList<>());
        }
        invoice.getPayments().add(payment);

        Invoice updatedInvoice = invoiceRepository.save(invoice);

        return PaymentCreationResponse.builder()
                .invoice(mapToResponse(updatedInvoice))
                .paymentUrl(checkoutUrl)
                .build();
    }

    @Override
    @Transactional
    public void updatePaymentSuccess(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NoSuchElementException("Invoice not found with ID: " + invoiceId));
        
        invoice.setStatus(Invoice.InvoiceStatus.completed);

        invoice.getPayments().stream()
               .filter(p -> p.getStatus() == Payment.PaymentStatus.PENDING) // Cập nhật payment PENDING gần nhất
               .findFirst()
               .ifPresent(p -> {
                   p.setStatus(Payment.PaymentStatus.COMPLETED);
                   p.setPaidAt(java.time.LocalDateTime.now(java.time.ZoneId.systemDefault()));
               });

        invoiceRepository.save(invoice);
    }

    @Override
    @Transactional
    public void updatePaymentFailed(Long invoiceId) {
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new NoSuchElementException("Invoice not found with ID: " + invoiceId));

        if (invoice.getStatus() == Invoice.InvoiceStatus.pending) {
            // Không thay đổi trạng thái hóa đơn, chỉ cập nhật payment
            // Hóa đơn vẫn là "pending" để có thể thử thanh toán lại
            invoice.getPayments().stream()
                   .filter(p -> p.getStatus() == Payment.PaymentStatus.PENDING)
                   .findFirst()
                   .ifPresent(p -> p.setStatus(Payment.PaymentStatus.FAILED));
            
            // Lưu ý: Không hoàn kho ở đây nữa vì người dùng có thể muốn thử lại ngay
            // Logic hoàn kho nên được xử lý khi hủy toàn bộ hóa đơn.
            invoiceRepository.save(invoice);
        }
    }

    private PaymentData createPayOSData(Invoice invoice) {
        String description = "Thanh toán đơn hàng #" + invoice.getId();
        List<ItemData> items = invoice.getInvoiceItems().stream()
                .map(invoiceItem -> ItemData.builder()
                        .name(invoiceItem.getProduct().getName())
                        .quantity(invoiceItem.getQuantity())
                        .price(invoiceItem.getUnitPrice().intValue())
                        .build())
                .collect(Collectors.toList());

        return PaymentData.builder()
            .orderCode(invoice.getId())
            .amount(invoice.getTotalAmount().intValue())
            .description(description)
            .items(items)
            .cancelUrl(frontendUrl + "/payment/cancel/" + invoice.getId())
            .returnUrl(frontendUrl + "/payment/success/" + invoice.getId())
            .build();
    }

    // =================================================================
    // CÁC PHƯƠNG THỨC KHÁC (GIỮ NGUYÊN)
    // =================================================================

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse getById(Long id) {
        Invoice invoice = invoiceRepository.findByIdWithItems(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice not found with ID: " + id));
        return mapToResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getByStatus(Invoice.InvoiceStatus status) {
        return invoiceRepository.findByStatus(status).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public InvoiceResponse updateStatus(Long id, Invoice.InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Invoice not found with ID: " + id));
        
        // Logic hoàn kho khi huỷ hoá đơn
        if (status == Invoice.InvoiceStatus.cancelled && invoice.getStatus() == Invoice.InvoiceStatus.pending) {
            for (InvoiceItem item : invoice.getInvoiceItems()) {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }
        
        invoice.setStatus(status);
        return mapToResponse(invoiceRepository.save(invoice));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Invoice invoice = invoiceRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Invoice not found with ID: " + id));
        
        // Logic hoàn kho khi xoá hoá đơn nếu nó chưa hoàn thành
        if (invoice.getStatus() != Invoice.InvoiceStatus.completed) {
            for (InvoiceItem item : invoice.getInvoiceItems()) {
                Product product = item.getProduct();
                product.setQuantity(product.getQuantity() + item.getQuantity());
                productRepository.save(product);
            }
        }
        invoiceRepository.deleteById(id);
    }

    private InvoiceResponse mapToResponse(Invoice invoice) {
        List<InvoiceItemResponse> itemResponses = invoice.getInvoiceItems() != null ?
                invoice.getInvoiceItems().stream()
                        .map(ii -> InvoiceItemResponse.builder()
                                .id(ii.getId())
                                .productId(ii.getProduct().getId())
                                .productName(ii.getProduct().getName())
                                .quantity(ii.getQuantity())
                                .unitPrice(ii.getUnitPrice())
                                .itemTotal(ii.getItemTotal())
                                .build())
                        .collect(Collectors.toList()) : Collections.emptyList();

        List<PaymentResponse> paymentResponses = invoice.getPayments() != null ?
                invoice.getPayments().stream()
                        .map(p -> PaymentResponse.builder()
                                .id(p.getId())
                                .paymentMethodName(p.getPaymentMethod().getName())
                                .amount(p.getAmount())
                                .paidAt(p.getPaidAt())
                                .status(p.getStatus().name())
                                .createdAt(p.getCreatedAt())
                                .build())
                        .collect(Collectors.toList()) : Collections.emptyList();

        return InvoiceResponse.builder()
                .id(invoice.getId())
                .customerId(invoice.getCustomer() != null ? invoice.getCustomer().getId() : null)
                .employeeId(invoice.getEmployee().getId())
                .tableId(invoice.getTable() != null ? invoice.getTable().getId() : null)
                .discountId(invoice.getDiscount() != null ? invoice.getDiscount().getId() : null)
                .subTotal(invoice.getSubTotal())
                .discountAmount(invoice.getDiscountAmount())
                .taxAmount(invoice.getTaxAmount())
                .totalAmount(invoice.getTotalAmount())
                .status(invoice.getStatus().name())
                .note(invoice.getNote())
                .createdAt(invoice.getCreatedAt())
                .updatedAt(invoice.getUpdatedAt())
                .items(itemResponses)
                .payments(paymentResponses)
                .build();
    }
}