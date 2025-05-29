package com.pos.backend.dto.stock_out;

import lombok.Data;

@Data
public class StockOutRequest {
    private Long productId;
    private Integer quantity;
    private String note;
    private Long employeeId;
}
