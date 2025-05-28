/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.employee;

/**
 * @author 04dkh
 */
import com.pos.backend.dto.common.RoleDto; // Hoặc package phù hợp cho RoleDto
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id; // Từ BaseEntity
    private String name;
    private String username;
    // Không trả về passwordHash vì lý do bảo mật
    private RoleDto role; // Sử dụng RoleDto
    private String phone;
    private String email;
    private Boolean isActive;
    // Có thể thêm createdAt, updatedAt nếu có trong BaseEntity và bạn muốn trả về
    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;
}
