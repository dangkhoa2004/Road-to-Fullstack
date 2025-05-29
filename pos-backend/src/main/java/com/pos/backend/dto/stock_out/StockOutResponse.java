package com.pos.backend.dto.stock_out;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockOutResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private String note;
    private Long employeeId;
    private String employeeName;
}
