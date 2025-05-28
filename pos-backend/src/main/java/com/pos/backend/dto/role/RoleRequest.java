/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.role;

/**
 *
 * @author 04dkh
 */
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleRequest {

    @NotBlank(message = "Tên vai trò không được để trống")
    @Size(max = 50, message = "Tên vai trò không được quá 50 ký tự")
    private String name;
}
