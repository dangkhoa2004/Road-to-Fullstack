/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 * @author 04dkh
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.system_setting.SystemSettingRequest;
import com.pos.backend.dto.system_setting.SystemSettingResponse;
import com.pos.backend.service.base.SystemSettingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/setting")
@RequiredArgsConstructor
public class SystemSettingController {

    private final SystemSettingService settingService;

    // Get setting
    @GetMapping
    public ResponseEntity<ApiResponse<SystemSettingResponse>> getSetting() {
        try {
            SystemSettingResponse response = settingService.getSetting();
            ApiResponse<SystemSettingResponse> apiResponse = new ApiResponse<>("Lấy cài đặt thành công", "200",
                    response);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<SystemSettingResponse> errorResponse = new ApiResponse<>("Có lỗi xảy ra: " + e.getMessage(),
                    "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Update setting
    @PutMapping
    public ResponseEntity<ApiResponse<SystemSettingResponse>> updateSetting(
            @Valid @RequestBody SystemSettingRequest request) {
        try {
            SystemSettingResponse updatedSetting = settingService.updateSetting(request);
            ApiResponse<SystemSettingResponse> apiResponse = new ApiResponse<>("Cập nhật cài đặt thành công", "200",
                    updatedSetting);
            return ResponseEntity.ok(apiResponse);
        } catch (Exception e) {
            ApiResponse<SystemSettingResponse> errorResponse = new ApiResponse<>(
                    "Cập nhật cài đặt thất bại: " + e.getMessage(), "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
