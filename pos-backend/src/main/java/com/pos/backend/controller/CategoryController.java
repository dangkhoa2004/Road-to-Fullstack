/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 * @author 04dkh
 */

import com.pos.backend.dto.category.CategoryRequest;
import com.pos.backend.dto.category.CategoryResponse;
import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.service.base.CategoryService;
import jakarta.validation.Valid;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> getAllCategories() {
        List<CategoryResponse> categories = categoryService.getAllCategories();
        ApiResponse<List<CategoryResponse>> apiResponse = new ApiResponse<>("Lấy danh sách danh mục thành công", "200",
                categories);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> getCategoryById(@PathVariable Long id) {
        try {
            CategoryResponse category = categoryService.getCategoryById(id);
            ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>("Lấy thông tin danh mục thành công", "200",
                    category);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>("Không tìm thấy danh mục với ID: " + id,
                    "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>(
                    "Có lỗi xảy ra khi lấy danh mục: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CategoryResponse>> createCategory(
            @Valid @RequestBody CategoryRequest categoryRequest) {
        try {
            CategoryResponse createdCategory = categoryService.createCategory(categoryRequest);
            ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>("Tạo danh mục thành công", "201",
                    createdCategory);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>(
                    "Tên danh mục đã tồn tại hoặc dữ liệu không hợp lệ.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>("Tạo danh mục thất bại: " + e.getMessage(),
                    "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> updateCategory(@PathVariable Long id,
            @Valid @RequestBody CategoryRequest categoryRequest) {
        try {
            CategoryResponse updatedCategory = categoryService.updateCategory(id, categoryRequest);
            ApiResponse<CategoryResponse> apiResponse = new ApiResponse<>("Cập nhật danh mục thành công", "200",
                    updatedCategory);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>(
                    "Không tìm thấy danh mục để cập nhật với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>(
                    "Tên danh mục đã tồn tại hoặc dữ liệu không hợp lệ.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<CategoryResponse> errorResponse = new ApiResponse<>(
                    "Cập nhật danh mục thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategory(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>("Xóa danh mục thành công", "204", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Không tìm thấy danh mục để xóa với ID: " + id, "404",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Xóa danh mục thất bại: " + e.getMessage(), "500",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
