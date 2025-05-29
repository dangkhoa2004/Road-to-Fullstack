/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.employee;

/**
 * @author 04dkh
 */

import com.pos.backend.dto.role.RoleResponse;
import com.pos.backend.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String name;
    private String username;
    private RoleResponse role; // Dùng RoleResponse thay vì RoleDto
    private String phone;
    private String email;
    private Boolean isActive;

    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.username = employee.getUsername();
        this.phone = employee.getPhone();
        this.email = employee.getEmail();
        this.isActive = employee.getIsActive();
        if (employee.getRole() != null) {
            this.role = new RoleResponse(employee.getRole());
        }
    }
}

