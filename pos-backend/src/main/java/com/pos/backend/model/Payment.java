package com.pos.backend.model;

import com.pos.backend.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "payments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_method_id", nullable = false)
    private PaymentMethod paymentMethod;
    @Column(name = "amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal amount;
    /**
     * The exact date and time when the payment was confirmed as completed.
     * This can be null if the payment is still pending.
     */
    @Column(name = "paid_at") // Bỏ `nullable = false` để cho phép giá trị null
    private LocalDateTime paidAt; // Bỏ giá trị mặc định `= LocalDateTime.now()`
    /**
     * The current status of the payment.
     * This is set by the service layer, not defaulted here.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private PaymentStatus status; // QUAN TRỌNG: KHÔNG CÓ "= PaymentStatus.COMPLETED"
    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "transaction_code") // Mã giao dịch của payOS
    private String transactionCode;

    @Column(name = "checkout_url", length = 500) // URL thanh toán
    private String checkoutUrl;

    /**
     * Enum representing the status of the payment.
     */
    public enum PaymentStatus {
        /**
         * The payment is pending confirmation.
         */
        PENDING,
        /**
         * The payment was successfully completed.
         */
        COMPLETED,
        /**
         * The payment failed (e.g., card declined).
         */
        FAILED,
        /**
         * The payment was successfully completed and later refunded.
         */
        REFUNDED
    }
}
