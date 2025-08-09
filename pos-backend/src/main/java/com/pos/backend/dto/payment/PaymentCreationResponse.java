package com.pos.backend.dto.payment;

import com.pos.backend.dto.invoice.InvoiceResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentCreationResponse {
    private InvoiceResponse invoice; // Thông tin hóa đơn đã được cập nhật
    private String paymentUrl;       // URL thanh toán của PayOS (nếu có)
}