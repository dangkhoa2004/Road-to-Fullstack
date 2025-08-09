package com.pos.backend.dto.invoice;

import com.pos.backend.dto.payment.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceRequest {
    private Long customerId;
    private Long employeeId;
    private Long tableId;
    private Long discountId;
    private List<InvoiceItemRequest> items;
    private String note;
    private List<PaymentRequest> payments;
}
