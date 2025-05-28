/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.model;

/**
 * @author 04dkh
 */
import com.pos.backend.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "invoices")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Invoice extends BaseEntity {

    public enum InvoiceStatus {
        pending, completed, cancelled, refunded
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "table_id")
    private Tables table;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "discount_id")
    private Discount discount;

    @Column(name = "sub_total", nullable = false, precision = 12, scale = 2)
    private BigDecimal subTotal = BigDecimal.ZERO;

    @Column(name = "discount_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal discountAmount = BigDecimal.ZERO;

    @Column(name = "tax_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal taxAmount = BigDecimal.ZERO;

    @Column(name = "total_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 10)
    private InvoiceStatus status = InvoiceStatus.pending;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceItem> invoiceItems;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Payment> payments;

    // No need for @Version here if it's already in BaseEntity
    // private Long version; // Remove this line if BaseEntity handles it
}
