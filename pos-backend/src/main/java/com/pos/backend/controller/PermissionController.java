package com.pos.backend.controller;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.permission.PermissionRequest;
import com.pos.backend.dto.permission.PermissionResponse;
import com.pos.backend.service.base.PermissionService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/permission")
public class PermissionController {

    private final PermissionService permissionService;

    public PermissionController(PermissionService permissionService) {
        this.permissionService = permissionService;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<PermissionResponse>>> getAllPermissions() {
        List<PermissionResponse> permissions = permissionService.getAllPermissions();
        ApiResponse<List<PermissionResponse>> apiResponse = new ApiResponse<>("Lấy danh sách quyền thành công", "200",
                permissions);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> getPermissionById(@PathVariable Long id) {
        try {
            PermissionResponse permission = permissionService.getPermissionById(id);
            ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>("Lấy thông tin quyền thành công", "200",
                    permission);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>("Không tìm thấy quyền với ID: " + id,
                    "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>(
                    "Có lỗi xảy ra khi lấy quyền: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse<PermissionResponse>> createPermission(
            @Valid @RequestBody PermissionRequest permissionRequest) {
        try {
            PermissionResponse createdPermission = permissionService.createPermission(permissionRequest);
            ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>("Tạo quyền thành công", "201",
                    createdPermission);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>(
                    "Tên quyền đã tồn tại hoặc dữ liệu không hợp lệ.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>("Tạo quyền thất bại: " + e.getMessage(),
                    "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PermissionResponse>> updatePermission(@PathVariable Long id,
                                                                            @Valid @RequestBody PermissionRequest permissionRequest) {
        try {
            PermissionResponse updatedPermission = permissionService.updatePermission(id, permissionRequest);
            ApiResponse<PermissionResponse> apiResponse = new ApiResponse<>("Cập nhật quyền thành công", "200",
                    updatedPermission);
            return ResponseEntity.ok(apiResponse);
        } catch (NoSuchElementException e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>(
                    "Không tìm thấy quyền để cập nhật với ID: " + id, "404", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (IllegalArgumentException e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>(
                    "Tên quyền đã tồn tại hoặc dữ liệu không hợp lệ.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (Exception e) {
            ApiResponse<PermissionResponse> errorResponse = new ApiResponse<>(
                    "Cập nhật quyền thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deletePermission(@PathVariable Long id) {
        try {
            permissionService.deletePermission(id);
            ApiResponse<Void> apiResponse = new ApiResponse<>("Xóa quyền thành công", "204", null);
            return new ResponseEntity<>(apiResponse, HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Không tìm thấy quyền để xóa với ID: " + id, "404",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>("Xóa quyền thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
