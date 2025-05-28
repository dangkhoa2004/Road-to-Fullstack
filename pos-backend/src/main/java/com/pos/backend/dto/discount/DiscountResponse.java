/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.discount;

/**
 * @author 04dkh
 */
import com.pos.backend.dto.common.DiscountTypeDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountResponse {

    private Long id;
    private String code;
    private String description;
    private DiscountTypeDto discountType;
    private BigDecimal value;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Boolean active;
    private BigDecimal minimumOrderAmount;
    private BigDecimal maximumDiscountAmount;
}
