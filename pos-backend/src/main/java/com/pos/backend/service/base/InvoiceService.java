package com.pos.backend.service.base;

import com.pos.backend.dto.invoice.InvoiceRequest;
import com.pos.backend.dto.invoice.InvoiceResponse;
import com.pos.backend.dto.payment.PaymentCreationResponse;
import com.pos.backend.dto.payment.PaymentRequest;
import com.pos.backend.model.Invoice;

import java.util.List;

public interface InvoiceService {

    /**
     * Chỉ tạo hóa đơn, không xử lý thanh toán.
     * Trả về chi tiết hóa đơn vừa tạo.
     */
    InvoiceResponse create(InvoiceRequest request);

    /**
     * Thêm một khoản thanh toán vào hóa đơn đã tồn tại.
     * Trả về thông tin hóa đơn đã cập nhật và link PayOS nếu có.
     */
    PaymentCreationResponse addPaymentToInvoice(Long invoiceId, PaymentRequest paymentRequest);

    /**
     * Cập nhật trạng thái hóa đơn thành công sau khi PayOS trả về.
     */
    void updatePaymentSuccess(Long invoiceId);

    /**
     * Cập nhật trạng thái hóa đơn thất bại sau khi PayOS trả về.
     */
    void updatePaymentFailed(Long invoiceId);

    // --- Các phương thức khác ---
    List<InvoiceResponse> getAllInvoices();

    InvoiceResponse getById(Long id);

    List<InvoiceResponse> getByStatus(Invoice.InvoiceStatus status);

    InvoiceResponse updateStatus(Long id, Invoice.InvoiceStatus status);

    void delete(Long id);
}