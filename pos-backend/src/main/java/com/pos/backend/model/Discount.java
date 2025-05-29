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
import java.time.LocalDateTime;

@Entity
@Table(name = "discounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Discount extends BaseEntity {

    @Column(name = "code", nullable = false, unique = true, length = 50)
    private String code;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "discount_type_id", nullable = false)
    private DiscountType discountType;

    @Column(name = "value", nullable = false, precision = 10, scale = 2)
    private BigDecimal value;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @Column(name = "minimum_order_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal minimumOrderAmount = BigDecimal.ZERO;

    @Column(name = "maximum_discount_amount", nullable = false, precision = 12, scale = 2)
    private BigDecimal maximumDiscountAmount = BigDecimal.ZERO;

    public BigDecimal calculate(BigDecimal subTotal) {
        if (subTotal.compareTo(minimumOrderAmount) < 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal discountAmount = BigDecimal.ZERO;

        if (discountType != null && discountType.getName().equalsIgnoreCase("PERCENT")) {
            discountAmount = subTotal.multiply(value).divide(new BigDecimal("100"));
        } else if (discountType != null && discountType.getName().equalsIgnoreCase("FIXED")) {
            discountAmount = value;
        }

        if (maximumDiscountAmount != null && maximumDiscountAmount.compareTo(BigDecimal.ZERO) > 0) {
            discountAmount = discountAmount.min(maximumDiscountAmount);
        }

        return discountAmount;
    }

}
