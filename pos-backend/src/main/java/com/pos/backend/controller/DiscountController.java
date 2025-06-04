/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 * @author 04dkh
 */
import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.discount.DiscountRequest;
import com.pos.backend.dto.discount.DiscountResponse;
import com.pos.backend.service.base.DiscountService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/discount")
public class DiscountController {

    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<DiscountResponse>>> getAllDiscounts() {
        List<DiscountResponse> discounts = discountService.getAllDiscounts();
        ApiResponse<List<DiscountResponse>> apiResponse = new ApiResponse<>("Lấy danh sách khuyến mãi thành công",
                "200", discounts);
        return ResponseEntity.ok(apiResponse);
    }

    // Sửa đổi từ /id sang /code để tìm theo code
    @GetMapping("/code/{code}")
    public ResponseEntity<ApiResponse<DiscountResponse>> getDiscountByCode(@PathVariable String code) {
        try {
            DiscountResponse discount = discountService.getDiscountByCode(code); // Gọi phương thức mới
            ApiResponse<DiscountResponse> apiResponse = new ApiResponse<>("Lấy thông tin khuyến mãi thành công", "200",
                    discount);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>("Không tìm thấy khuyến mãi với mã: " + code,
                    "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>(
                    "Có lỗi xảy ra khi lấy khuyến mãi: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<DiscountResponse>> createDiscount(
            @Valid @RequestBody DiscountRequest discountRequest) {
        try {
            DiscountResponse createdDiscount = discountService.createDiscount(discountRequest);
            ApiResponse<DiscountResponse> apiResponse = new ApiResponse<>("Tạo khuyến mãi thành công", "201",
                    createdDiscount);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>(
                    "Mã khuyến mãi đã tồn tại hoặc dữ liệu không hợp lệ.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>(
                    "Tạo khuyến mãi thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Vẫn giữ update theo ID, vì ID là định danh ổn định cho một bản ghi
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<DiscountResponse>> updateDiscount(@PathVariable Long id,
            @Valid @RequestBody DiscountRequest discountRequest) {
        try {
            DiscountResponse updatedDiscount = discountService.updateDiscount(id, discountRequest);
            ApiResponse<DiscountResponse> apiResponse = new ApiResponse<>("Cập nhật khuyến mãi thành công", "200",
                    updatedDiscount);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>(
                    "Không tìm thấy khuyến mãi để cập nhật với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>(
                    "Mã khuyến mãi đã tồn tại hoặc dữ liệu không hợp lệ.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<DiscountResponse> errorResponse = new ApiResponse<>(
                    "Cập nhật khuyến mãi thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Vẫn giữ delete theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDiscount(@PathVariable Long id) {
        try {
            discountService.deleteDiscount(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>("Xóa khuyến mãi thành công", "204", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Không tìm thấy khuyến mãi để xóa với ID: " + id, "404",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Xóa khuyến mãi thất bại: " + e.getMessage(), "500",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
