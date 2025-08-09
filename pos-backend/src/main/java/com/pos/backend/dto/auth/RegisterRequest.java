/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.dto.auth;

/**
 * @author 04dkh
 */

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data // This Lombok annotation will generate getters and setters for all fields
@NoArgsConstructor // Lombok constructor
@AllArgsConstructor // Lombok constructor
public class RegisterRequest {

    @NotBlank(message = "Name is required") // Added validation for name
    private String name; // <--- ADDED THIS FIELD

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    private String password;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Phone number is required") // Added validation for phone
    @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits") // Example size validation
    private String phone; // <--- ADDED THIS FIELD
}
