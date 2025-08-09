/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 * @author 04dkh
 */

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.stock_in.StockInRequest;
import com.pos.backend.dto.stock_in.StockInResponse;
import com.pos.backend.dto.stock_out.StockOutRequest;
import com.pos.backend.dto.stock_out.StockOutResponse;
import com.pos.backend.service.base.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    // Create StockIn
    @PostMapping("/in")
    public ResponseEntity<ApiResponse<StockInResponse>> createStockIn(@RequestBody StockInRequest request) {
        StockInResponse response = stockService.createStockIn(request);
        ApiResponse<StockInResponse> apiResponse = new ApiResponse<>("Tạo phiếu nhập kho thành công", "201", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // Create StockOut
    @PostMapping("/out")
    public ResponseEntity<ApiResponse<StockOutResponse>> createStockOut(@RequestBody StockOutRequest request) {
        StockOutResponse response = stockService.createStockOut(request);
        ApiResponse<StockOutResponse> apiResponse = new ApiResponse<>("Tạo phiếu xuất kho thành công", "201", response);
        return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
    }

    // Get all StockIn
    @GetMapping("/in/list")
    public ResponseEntity<ApiResponse<List<StockInResponse>>> getAllStockIn() {
        List<StockInResponse> stockIns = stockService.getAllStockIn();
        ApiResponse<List<StockInResponse>> apiResponse = new ApiResponse<>("Lấy danh sách phiếu nhập kho thành công",
                "200", stockIns);
        return ResponseEntity.ok(apiResponse);
    }

    // Get all StockOut
    @GetMapping("/out/list")
    public ResponseEntity<ApiResponse<List<StockOutResponse>>> getAllStockOut() {
        List<StockOutResponse> stockOuts = stockService.getAllStockOut();
        ApiResponse<List<StockOutResponse>> apiResponse = new ApiResponse<>("Lấy danh sách phiếu xuất kho thành công",
                "200", stockOuts);
        return ResponseEntity.ok(apiResponse);
    }
}
