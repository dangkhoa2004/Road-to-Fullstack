/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.product;

/**
 *
 * @author 04dkh
 */
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ProductRequest {

    private String barcode; // Barcode can be null for new products if not required
    @NotBlank(message = "Product name cannot be empty")
    private String name;
    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be non-negative")
    private BigDecimal price;
    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity must be non-negative")
    private Integer quantity;
    private String imagePath;
    @NotNull(message = "Category ID cannot be null")
    private Long categoryId;
    private Boolean isActive = true; // Default to true if not provided

    
}
