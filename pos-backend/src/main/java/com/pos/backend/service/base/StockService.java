/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service.base;

/**
 * @author 04dkh
 */

import java.util.List;

import com.pos.backend.dto.stock_in.StockInRequest;
import com.pos.backend.dto.stock_in.StockInResponse;
import com.pos.backend.dto.stock_out.StockOutRequest;
import com.pos.backend.dto.stock_out.StockOutResponse;

public interface StockService {
    StockInResponse createStockIn(StockInRequest request);

    StockOutResponse createStockOut(StockOutRequest request);

    List<StockInResponse> getAllStockIn();

    List<StockOutResponse> getAllStockOut();
}
