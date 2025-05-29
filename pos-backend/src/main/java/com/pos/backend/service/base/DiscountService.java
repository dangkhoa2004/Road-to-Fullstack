/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service.base;

/**
 * @author 04dkh
 */
import com.pos.backend.dto.discount.DiscountRequest;
import com.pos.backend.dto.discount.DiscountResponse;

import java.util.List;

public interface DiscountService {

    List<DiscountResponse> getAllDiscounts();

    // Sửa đổi từ getDiscountById sang getDiscountByCode
    DiscountResponse getDiscountByCode(String code);

    DiscountResponse createDiscount(DiscountRequest discountRequest);

    DiscountResponse updateDiscount(Long id, DiscountRequest discountRequest); // Giữ update theo ID

    void deleteDiscount(Long id); // Giữ delete theo ID
}
