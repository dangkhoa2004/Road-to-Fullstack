package com.pos.backend.controller;

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.invoice.InvoiceRequest;
import com.pos.backend.dto.invoice.InvoiceResponse;
import com.pos.backend.dto.payment.PaymentCreationResponse;
import com.pos.backend.dto.payment.PaymentRequest;
import com.pos.backend.model.Invoice;
import com.pos.backend.service.base.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getInvoices(
            @RequestParam(required = false) Invoice.InvoiceStatus status) {
        List<InvoiceResponse> invoices = (status != null)
                ? invoiceService.getByStatus(status)
                : invoiceService.getAllInvoices();
        return ResponseEntity.ok(new ApiResponse<>("Lấy danh sách hoá đơn thành công", "200", invoices));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        InvoiceResponse createdInvoice = invoiceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Tạo hoá đơn thành công", "201", createdInvoice));
    }

    @PostMapping("/{invoiceId}/payments")
    public ResponseEntity<ApiResponse<PaymentCreationResponse>> addPayment(
            @PathVariable Long invoiceId, @Valid @RequestBody PaymentRequest paymentRequest) {
        PaymentCreationResponse paymentResponse = invoiceService.addPaymentToInvoice(invoiceId, paymentRequest);
        String message = (paymentResponse.getPaymentUrl() != null && !paymentResponse.getPaymentUrl().isEmpty())
                ? "Yêu cầu thanh toán đã được tạo. Vui lòng chuyển hướng tới URL thanh toán."
                : "Thanh toán đã được ghi nhận thành công.";
        return ResponseEntity.ok(new ApiResponse<>(message, "200", paymentResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceById(@PathVariable Long id) {
        InvoiceResponse invoice = invoiceService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>("Lấy hoá đơn thành công", "200", invoice));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<InvoiceResponse>> updateInvoiceStatus(
            @PathVariable Long id, @RequestParam Invoice.InvoiceStatus status) {
        InvoiceResponse updatedInvoice = invoiceService.updateStatus(id, status);
        return ResponseEntity.ok(new ApiResponse<>("Cập nhật trạng thái hoá đơn thành công", "200", updatedInvoice));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInvoice(@PathVariable Long id) {
        invoiceService.delete(id);
        return ResponseEntity.noContent().build(); // Chuẩn REST, không trả body khi 204
    }
}
