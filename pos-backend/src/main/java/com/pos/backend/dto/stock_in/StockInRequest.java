package com.pos.backend.dto.stock_in;

import lombok.Data;

@Data
public class StockInRequest {
    private Long productId;
    private Integer quantity;
    private String note;
    private Long employeeId;
}

