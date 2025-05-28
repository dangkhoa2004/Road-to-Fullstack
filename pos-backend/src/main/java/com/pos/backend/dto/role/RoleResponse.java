/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.role;

/**
 * @author 04dkh
 */
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleResponse {

    private Long id; // Từ BaseEntity
    private String name;
    // Không bao gồm Set<Employee> để tránh vòng lặp và giữ DTO gọn gàng
    // private LocalDateTime createdAt; // Nếu có trong BaseEntity và bạn muốn trả về
    // private LocalDateTime updatedAt; // Nếu có
}
