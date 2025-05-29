/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service.Impl;

/**
 * @author 04dkh
 */

import com.pos.backend.dto.common.DiscountTypeDto;
import com.pos.backend.dto.discount.DiscountRequest;
import com.pos.backend.dto.discount.DiscountResponse;
import com.pos.backend.model.Discount;
import com.pos.backend.model.DiscountType;
import com.pos.backend.repository.DiscountRepository;
import com.pos.backend.repository.DiscountTypeRepository;
import com.pos.backend.service.base.DiscountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class DiscountServiceImpl implements DiscountService {

    private final DiscountRepository discountRepository;
    private final DiscountTypeRepository discountTypeRepository;

    public DiscountServiceImpl(DiscountRepository discountRepository, DiscountTypeRepository discountTypeRepository) {
        this.discountRepository = discountRepository;
        this.discountTypeRepository = discountTypeRepository;
    }

    private DiscountResponse mapToResponse(Discount discount) {
        if (discount == null) {
            return null;
        }
        DiscountTypeDto discountTypeDto = null;
        if (discount.getDiscountType() != null) {
            discountTypeDto = new DiscountTypeDto(
                    discount.getDiscountType().getId(),
                    discount.getDiscountType().getName(),
                    discount.getDiscountType().getDescription()
            );
        }

        return new DiscountResponse(
                discount.getId(),
                discount.getCode(),
                discount.getDescription(),
                discountTypeDto,
                discount.getValue(),
                discount.getStartDate(),
                discount.getEndDate(),
                discount.getActive(),
                discount.getMinimumOrderAmount(),
                discount.getMaximumDiscountAmount()
        );
    }

    private Discount mapToEntity(DiscountRequest request, Discount discount) {
        if (request == null) {
            return null;
        }
        if (discount == null) {
            discount = new Discount();
        }

        discount.setCode(request.getCode());
        discount.setDescription(request.getDescription());
        discount.setValue(request.getValue());
        discount.setStartDate(request.getStartDate());
        discount.setEndDate(request.getEndDate());
        if (request.getActive() != null) {
            discount.setActive(request.getActive());
        } else if (discount.getId() == null) {
            discount.setActive(true);
        }

        discount.setMinimumOrderAmount(request.getMinimumOrderAmount());
        discount.setMaximumDiscountAmount(request.getMaximumDiscountAmount());

        DiscountType discountType = discountTypeRepository.findById(request.getDiscountTypeId())
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy loại khuyến mãi với ID: " + request.getDiscountTypeId()));
        discount.setDiscountType(discountType);

        return discount;
    }

    private void validateDiscountDates(LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Ngày kết thúc phải sau ngày bắt đầu.");
        }
    }

    @Override
    public List<DiscountResponse> getAllDiscounts() {
        return discountRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Triển khai phương thức getDiscountByCode
    @Override
    public DiscountResponse getDiscountByCode(String code) {
        Discount discount = discountRepository.findByCode(code) // Dùng findByCode
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy khuyến mãi với mã: " + code));
        return mapToResponse(discount);
    }

    @Override
    @Transactional
    public DiscountResponse createDiscount(DiscountRequest discountRequest) {
        validateDiscountDates(discountRequest.getStartDate(), discountRequest.getEndDate());

        if (discountRepository.findByCode(discountRequest.getCode()).isPresent()) {
            throw new IllegalArgumentException("Mã khuyến mãi đã tồn tại: " + discountRequest.getCode());
        }

        Discount discount = mapToEntity(discountRequest, null);

        Discount savedDiscount = discountRepository.save(discount);
        return mapToResponse(savedDiscount);
    }

    @Override
    @Transactional
    public DiscountResponse updateDiscount(Long id, DiscountRequest discountRequest) {
        Discount existingDiscount = discountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy khuyến mãi để cập nhật với ID: " + id));

        validateDiscountDates(discountRequest.getStartDate(), discountRequest.getEndDate());

        if (!existingDiscount.getCode().equals(discountRequest.getCode())) {
            if (discountRepository.findByCode(discountRequest.getCode()).isPresent()) {
                throw new IllegalArgumentException("Mã khuyến mãi đã tồn tại: " + discountRequest.getCode());
            }
        }

        Discount updatedDiscount = mapToEntity(discountRequest, existingDiscount);

        Discount savedDiscount = discountRepository.save(updatedDiscount);
        return mapToResponse(savedDiscount);
    }

    @Override
    @Transactional
    public void deleteDiscount(Long id) {
        if (!discountRepository.existsById(id)) {
            throw new NoSuchElementException("Không tìm thấy khuyến mãi để xóa với ID: " + id);
        }
        discountRepository.deleteById(id);
    }
}
