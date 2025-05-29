/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 * @author 04dkh
 */
import com.pos.backend.dto.common.ApiResponse; // Import ApiResponse
import com.pos.backend.dto.product.ProductRequest;
import com.pos.backend.dto.product.ProductResponse;
import com.pos.backend.service.base.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException; // Import này để xử lý Not Found

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Get all products
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = productService.getAllProducts();
        ApiResponse<List<ProductResponse>> apiResponse = new ApiResponse<>("Lấy danh sách sản phẩm thành công", "200", products);
        return ResponseEntity.ok(apiResponse);
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        try {
            ProductResponse product = productService.getProductById(id);
            ApiResponse<ProductResponse> apiResponse = new ApiResponse<>("Lấy thông tin sản phẩm thành công", "200", product);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            // Xử lý trường hợp không tìm thấy sản phẩm
            ApiResponse<ProductResponse> errorResponse = new ApiResponse<>("Không tìm thấy sản phẩm với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            // Xử lý các lỗi khác
            ApiResponse<ProductResponse> errorResponse = new ApiResponse<>("Có lỗi xảy ra khi lấy sản phẩm: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new product
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<ProductResponse>> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        try {
            ProductResponse createdProduct = productService.createProduct(productRequest);
            ApiResponse<ProductResponse> apiResponse = new ApiResponse<>("Tạo sản phẩm thành công", "201", createdProduct);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            // Xử lý lỗi khi tạo sản phẩm (ví dụ: dữ liệu không hợp lệ, tên trùng lặp)
            ApiResponse<ProductResponse> errorResponse = new ApiResponse<>("Tạo sản phẩm thất bại: " + e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Update an existing product
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        try {
            ProductResponse updatedProduct = productService.updateProduct(id, productRequest);
            ApiResponse<ProductResponse> apiResponse = new ApiResponse<>("Cập nhật sản phẩm thành công", "200", updatedProduct);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<ProductResponse> errorResponse = new ApiResponse<>("Không tìm thấy sản phẩm để cập nhật với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<ProductResponse> errorResponse = new ApiResponse<>("Cập nhật sản phẩm thất bại: " + e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    // Delete a product
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            // Khi xóa thành công, data là null hoặc một đối tượng trống
            ApiResponse<Void> apiResponse = new ApiResponse<>("Xóa sản phẩm thành công", "204", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT); // 204 No Content
        } catch (NoSuchElementException e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Không tìm thấy sản phẩm để xóa với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Xóa sản phẩm thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
