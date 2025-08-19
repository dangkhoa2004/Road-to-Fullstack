package com.pos.backend.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.invoice.InvoiceRequest;
import com.pos.backend.dto.invoice.InvoiceResponse;
import com.pos.backend.dto.payment.PaymentCreationResponse;
import com.pos.backend.dto.payment.PaymentRequest;
import com.pos.backend.model.Invoice;
import com.pos.backend.service.base.InvoiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

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

    // Tạo hoá đơn mới
    // Trả về mã 201 Created và thông báo thành công
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        InvoiceResponse createdInvoice = invoiceService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Tạo hoá đơn thành công", "201", createdInvoice));
    }

    // Thêm thanh toán vào hoá đơn
    // Nếu paymentUrl không rỗng, trả về thông báo yêu cầu thanh toán
    // Nếu paymentUrl rỗng, trả về thông báo thanh toán đã được ghi nhận
    @ResponseStatus(HttpStatus.OK)
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
