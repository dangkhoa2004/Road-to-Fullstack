// Trong tệp: PaymentResponse.java
package com.pos.backend.dto.payment;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class PaymentResponse {
    private Long id;
    private String paymentMethodName;
    private BigDecimal amount;
    private LocalDateTime paidAt;
    private String status; // Thêm trường này
    private LocalDateTime createdAt; // Thêm trường này
}