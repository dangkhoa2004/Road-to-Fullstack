/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.discount;

/**
 * @author 04dkh
 */
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiscountRequest {

    @NotBlank(message = "Mã khuyến mãi không được để trống")
    @Size(max = 50, message = "Mã khuyến mãi không được quá 50 ký tự")
    private String code;

    @Size(max = 1000, message = "Mô tả không được quá 1000 ký tự")
    private String description;

    @NotNull(message = "Loại khuyến mãi không được để trống")
    private Long discountTypeId; // Vẫn chỉ cần ID

    @NotNull(message = "Giá trị khuyến mãi không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá trị khuyến mãi phải lớn hơn 0")
    private BigDecimal value;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    @FutureOrPresent(message = "Ngày bắt đầu phải là hiện tại hoặc trong tương lai")
    private LocalDateTime startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    @FutureOrPresent(message = "Ngày kết thúc phải là hiện tại hoặc trong tương lai")
    private LocalDateTime endDate;

    private Boolean active;

    @NotNull(message = "Số tiền đơn hàng tối thiểu không được để trống")
    @DecimalMin(value = "0.0", message = "Số tiền đơn hàng tối thiểu phải lớn hơn hoặc bằng 0")
    private BigDecimal minimumOrderAmount;

    @NotNull(message = "Số tiền khuyến mãi tối đa không được để trống")
    @DecimalMin(value = "0.0", message = "Số tiền khuyến mãi tối đa phải lớn hơn hoặc bằng 0")
    private BigDecimal maximumDiscountAmount;
}
