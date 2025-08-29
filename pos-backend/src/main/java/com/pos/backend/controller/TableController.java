package com.pos.backend.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.tables.TableRequest;
import com.pos.backend.dto.tables.TableResponse;
import com.pos.backend.model.Tables;
import com.pos.backend.service.base.TableService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/tables")
@RequiredArgsConstructor
public class TableController {

    private final TableService tableService;

    // Get all tables
    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<TableResponse>>> getAllTables() {
        try {
            List<TableResponse> tables = tableService.getAll();
            ApiResponse<List<TableResponse>> apiResponse = new ApiResponse<>("Lấy danh sách bàn thành công", "200",
                    tables);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<List<TableResponse>> errorResponse = new ApiResponse<>(
                    "Có lỗi xảy ra khi lấy danh sách bàn: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get table by ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TableResponse>> getTableById(@PathVariable Long id) {
        try {
            TableResponse table = tableService.getById(id);
            ApiResponse<TableResponse> apiResponse = new ApiResponse<>("Lấy thông tin bàn thành công", "200", table);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>("Không tìm thấy bàn với ID: " + id, "404",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>("Có lỗi xảy ra khi lấy bàn: " + e.getMessage(),
                    "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Get tables by status
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<List<TableResponse>>> getTablesByStatus(@RequestParam Tables.TableStatus status) {
        try {
            List<TableResponse> tables = tableService.getByStatus(status);
            ApiResponse<List<TableResponse>> apiResponse = new ApiResponse<>("Lấy danh sách bàn thành công", "200",
                    tables);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<List<TableResponse>> errorResponse = new ApiResponse<>(
                    "Có lỗi xảy ra khi lấy danh sách bàn: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Create a new table
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TableResponse>> createTable(@Valid @RequestBody TableRequest request) {
        try {
            TableResponse createdTable = tableService.create(request);
            ApiResponse<TableResponse> apiResponse = new ApiResponse<>("Tạo bàn thành công", "201", createdTable);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>("Dữ liệu đầu vào vi phạm ràng buộc.", "409",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>("Tạo bàn thất bại: " + e.getMessage(), "500",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update an existing table
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TableResponse>> updateTable(@PathVariable Long id,
                                                                  @Valid @RequestBody TableRequest request) {
        try {
            TableResponse updatedTable = tableService.update(id, request);
            ApiResponse<TableResponse> apiResponse = new ApiResponse<>("Cập nhật bàn thành công", "200", updatedTable);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>("Không tìm thấy bàn để cập nhật với ID: " + id,
                    "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>("Dữ liệu đầu vào vi phạm ràng buộc.", "409",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>("Cập nhật bàn thất bại: " + e.getMessage(),
                    "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update table status
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<TableResponse>> updateTableStatus(@PathVariable Long id,
                                                                        @RequestParam Tables.TableStatus status) {
        try {
            TableResponse updatedTable = tableService.updateStatus(id, status);
            ApiResponse<TableResponse> apiResponse = new ApiResponse<>("Cập nhật trạng thái bàn thành công", "200",
                    updatedTable);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>("Không tìm thấy bàn để cập nhật với ID: " + id,
                    "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<TableResponse> errorResponse = new ApiResponse<>(
                    "Cập nhật trạng thái bàn thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Delete a table
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTable(@PathVariable Long id) {
        try {
            tableService.delete(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>("Xoá bàn thành công", "204", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Không tìm thấy bàn để xoá với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Xoá bàn thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
