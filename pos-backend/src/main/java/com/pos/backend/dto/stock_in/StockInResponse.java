package com.pos.backend.dto.stock_in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StockInResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private String note;
    private Long employeeId;
    private String employeeName;

}

