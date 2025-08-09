package com.pos.backend.dto.invoice;

import com.pos.backend.dto.payment.PaymentResponse;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class InvoiceResponse {
    private Long id;
    private Long customerId;
    private Long employeeId;
    private Long tableId;
    private Long discountId;
    private BigDecimal subTotal;
    private BigDecimal discountAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalAmount;
    private String status;
    private String note;
    private List<InvoiceItemResponse> items;
    private List<PaymentResponse> payments;

    // THÊM 2 TRƯỜNG NÀY VÀO
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}