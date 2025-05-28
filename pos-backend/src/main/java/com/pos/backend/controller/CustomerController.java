/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 * @author 04dkh
 */
import com.pos.backend.dto.common.ApiResponse; // Import ApiResponse
import com.pos.backend.dto.customer.CustomerRequest;
import com.pos.backend.dto.customer.CustomerResponse;
import com.pos.backend.service.CustomerService; // Import CustomerService
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.dao.DataIntegrityViolationException; // Để xử lý lỗi UNIQUE constraint
import java.util.List;
import java.util.NoSuchElementException; // Để xử lý Not Found
@RestController
@RequestMapping("/api/customer")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    // Get all customers
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<CustomerResponse>>> getAllCustomers() {
        List<CustomerResponse> customers = customerService.getAllCustomers();
        ApiResponse<List<CustomerResponse>> apiResponse = new ApiResponse<>("Lấy danh sách khách hàng thành công", "200", customers);
        return ResponseEntity.ok(apiResponse);
    }

    // Get customer by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> getCustomerById(@PathVariable Long id) {
        try {
            CustomerResponse customer = customerService.getCustomerById(id);
            ApiResponse<CustomerResponse> apiResponse = new ApiResponse<>("Lấy thông tin khách hàng thành công", "200", customer);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>("Không tìm thấy khách hàng với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>("Có lỗi xảy ra khi lấy khách hàng: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new customer
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<CustomerResponse>> createCustomer(@Valid @RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse createdCustomer = customerService.createCustomer(customerRequest);
            ApiResponse<CustomerResponse> apiResponse = new ApiResponse<>("Tạo khách hàng thành công", "201", createdCustomer);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            // Lỗi validation tùy chỉnh từ service (ví dụ: số điện thoại/email đã tồn tại)
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            // Lỗi liên quan đến ràng buộc cơ sở dữ liệu (ví dụ: phone/email trùng lặp)
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>("Số điện thoại hoặc Email đã tồn tại.", "409", null); // 409 Conflict
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>("Tạo khách hàng thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing customer
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CustomerResponse>> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest customerRequest) {
        try {
            CustomerResponse updatedCustomer = customerService.updateCustomer(id, customerRequest);
            ApiResponse<CustomerResponse> apiResponse = new ApiResponse<>("Cập nhật khách hàng thành công", "200", updatedCustomer);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>("Không tìm thấy khách hàng để cập nhật với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>("Số điện thoại hoặc Email đã tồn tại.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<CustomerResponse> errorResponse = new ApiResponse<>("Cập nhật khách hàng thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a customer
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCustomer(@PathVariable Long id) {
        try {
            customerService.deleteCustomer(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>("Xóa khách hàng thành công", "204", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Không tìm thấy khách hàng để xóa với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Xóa khách hàng thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
