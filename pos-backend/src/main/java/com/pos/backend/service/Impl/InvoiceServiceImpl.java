package com.pos.backend.service.Impl;

import com.pos.backend.dto.invoice.InvoiceItemResponse;
import com.pos.backend.dto.invoice.InvoiceRequest;
import com.pos.backend.dto.invoice.InvoiceResponse;
import com.pos.backend.model.Invoice;
import com.pos.backend.model.InvoiceItem;
import com.pos.backend.model.Product;
import com.pos.backend.repository.*;
import com.pos.backend.service.base.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InvoiceServiceImpl implements InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final InvoiceItemRepository invoiceItemRepository;
    private final ProductRepository productRepository; // assumed to exist
    private final CustomerRepository customerRepository; // assumed
    private final EmployeeRepository employeeRepository; // assumed
    private final TablesRepository tablesRepository; // assumed
    private final DiscountRepository discountRepository; // assumed

    /* ------------------------- GETALL ------------------------- */
    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    /* ------------------------- CREATE ------------------------- */
    @Override
    @Transactional
    public InvoiceResponse create(InvoiceRequest request) {
        Invoice invoice = new Invoice();
        invoice.setCustomer(customerRepository.findById(request.getCustomerId()).orElse(null));
        invoice.setEmployee(employeeRepository.findById(request.getEmployeeId()).orElseThrow());
        invoice.setTable(tablesRepository.findById(request.getTableId()).orElse(null));
        invoice.setDiscount(
                request.getDiscountId() != null ? discountRepository.findById(request.getDiscountId()).orElse(null)
                        : null);
        invoice.setNote(request.getNote());

        BigDecimal subTotal = BigDecimal.ZERO;
        List<InvoiceItem> items = new ArrayList<>();

        for (var itemReq : request.getItems()) {
            Product product = productRepository.findById(itemReq.getProductId()).orElseThrow();
            BigDecimal unitPrice = product.getPrice();
            BigDecimal itemTotal = unitPrice.multiply(BigDecimal.valueOf(itemReq.getQuantity()));
            subTotal = subTotal.add(itemTotal);

            InvoiceItem ii = new InvoiceItem();
            ii.setInvoice(invoice);
            ii.setProduct(product);
            ii.setQuantity(itemReq.getQuantity());
            ii.setUnitPrice(unitPrice);
            ii.setItemTotal(itemTotal);

            items.add(ii);
        }

        invoice.setSubTotal(subTotal);

        BigDecimal discountAmt = invoice.getDiscount() != null ? invoice.getDiscount().calculate(subTotal)
                : BigDecimal.ZERO;
        invoice.setDiscountAmount(discountAmt);
        BigDecimal taxAmt = subTotal.subtract(discountAmt).multiply(new BigDecimal("0.10"));
        invoice.setTaxAmount(taxAmt);
        invoice.setTotalAmount(subTotal.subtract(discountAmt).add(taxAmt));

        invoice.setInvoiceItems(items);
        invoiceRepository.save(invoice);

        return mapToResponse(invoice);
    }

    /* ------------------------- READ ------------------------- */
    @Override
    @Transactional(readOnly = true)
    public InvoiceResponse getById(Long id) {
        Invoice invoice = invoiceRepository.findByIdWithItems(id).orElseThrow();
        return mapToResponse(invoice);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InvoiceResponse> getByStatus(Invoice.InvoiceStatus status) {
        return invoiceRepository.findByStatus(status).stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /* ------------------------- UPDATE STATUS ------------------------- */
    @Override
    @Transactional
    public InvoiceResponse updateStatus(Long id, Invoice.InvoiceStatus status) {
        Invoice invoice = invoiceRepository.findById(id).orElseThrow();
        invoice.setStatus(status);
        return mapToResponse(invoice);
    }

    /* ------------------------- DELETE ------------------------- */
    @Override
    @Transactional
    public void delete(Long id) {
        invoiceRepository.deleteById(id);
    }

    /* ------------------------- MAPPER ------------------------- */
    private InvoiceResponse mapToResponse(Invoice invoice) {
        List<InvoiceItemResponse> itemResponses = invoice.getInvoiceItems().stream()
                .map(ii -> InvoiceItemResponse.builder()
                        .id(ii.getId())
                        .productId(ii.getProduct().getId())
                        .productName(ii.getProduct().getName())
                        .quantity(ii.getQuantity())
                        .unitPrice(ii.getUnitPrice())
                        .itemTotal(ii.getItemTotal())
                        .build())
                .collect(Collectors.toList());

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
                .items(itemResponses)
                .build();
    }
}
