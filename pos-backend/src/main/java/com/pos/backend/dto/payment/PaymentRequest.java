package com.pos.backend.dto.payment;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentRequest {
    private Long paymentMethodId;
    private BigDecimal amount;
}