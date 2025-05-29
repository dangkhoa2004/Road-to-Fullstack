package com.pos.backend.controller;

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.invoice.InvoiceRequest;
import com.pos.backend.dto.invoice.InvoiceResponse;
import com.pos.backend.model.Invoice;
import com.pos.backend.service.base.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/invoice")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    // Get all invoices (not filtered by status)
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<InvoiceResponse>>> getAllInvoices() {
        try {
            List<InvoiceResponse> invoices = invoiceService.getAllInvoices();
            ApiResponse<List<InvoiceResponse>> apiResponse = new ApiResponse<>("Lấy danh sách hoá đơn thành công", "200", invoices);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<List<InvoiceResponse>> errorResponse = new ApiResponse<>("Có lỗi xảy ra khi lấy danh sách hoá đơn: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new invoice
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<InvoiceResponse>> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        try {
            InvoiceResponse createdInvoice = invoiceService.create(request);
            ApiResponse<InvoiceResponse> apiResponse = new ApiResponse<>("Tạo hoá đơn thành công", "201", createdInvoice);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponse<InvoiceResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<InvoiceResponse> errorResponse = new ApiResponse<>("Dữ liệu đầu vào vi phạm ràng buộc.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<InvoiceResponse> errorResponse = new ApiResponse<>("Tạo hoá đơn thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get invoice by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<InvoiceResponse>> getInvoiceById(@PathVariable Long id) {
        try {
            InvoiceResponse invoice = invoiceService.getById(id);
            ApiResponse<InvoiceResponse> apiResponse = new ApiResponse<>("Lấy hoá đơn thành công", "200", invoice);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<InvoiceResponse> errorResponse = new ApiResponse<>("Không tìm thấy hoá đơn với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<InvoiceResponse> errorResponse = new ApiResponse<>("Có lỗi xảy ra khi lấy hoá đơn: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update status
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<InvoiceResponse>> updateInvoiceStatus(
            @PathVariable Long id,
            @RequestParam Invoice.InvoiceStatus status) {
        try {
            InvoiceResponse updatedInvoice = invoiceService.updateStatus(id, status);
            ApiResponse<InvoiceResponse> apiResponse = new ApiResponse<>("Cập nhật trạng thái hoá đơn thành công", "200", updatedInvoice);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<InvoiceResponse> errorResponse = new ApiResponse<>("Không tìm thấy hoá đơn để cập nhật với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<InvoiceResponse> errorResponse = new ApiResponse<>("Cập nhật trạng thái hoá đơn thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete an invoice
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteInvoice(@PathVariable Long id) {
        try {
            invoiceService.delete(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>("Xoá hoá đơn thành công", "204", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Không tìm thấy hoá đơn để xoá với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Xoá hoá đơn thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
