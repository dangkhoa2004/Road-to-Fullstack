/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.customer;

/**
 * @author 04dkh
 */
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerRequest {

    @NotBlank(message = "Tên khách hàng không được để trống")
    @Size(max = 255, message = "Tên khách hàng không được quá 255 ký tự")
    private String name;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^\\+?[0-9. ()-]{7,25}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @Email(message = "Email không hợp lệ")
    @Size(max = 255, message = "Email không được quá 255 ký tự")
    private String email;

    @Size(max = 1000, message = "Địa chỉ không được quá 1000 ký tự")
    private String address;
    // Không bao gồm loyaltyPoints trong request, vì nó thường được hệ thống quản lý
}
