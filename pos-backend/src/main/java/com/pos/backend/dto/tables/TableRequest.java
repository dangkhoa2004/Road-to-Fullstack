package com.pos.backend.dto.tables;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TableRequest {
    private String name;          // Tên bàn (bắt buộc)
    private String status;        // available | occupied | reserved | cleaning | out_of_service (tuỳ chọn)
    private Integer capacity;     // Sức chứa (bắt buộc)
}
