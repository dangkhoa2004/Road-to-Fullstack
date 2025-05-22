/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 *
 * @author 04dkh
 */
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping; // Import RegisterRequest DTO
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.dto.auth.AuthResponse; // Import HttpStatus
import com.pos.backend.dto.auth.LoginRequest;
import com.pos.backend.dto.auth.RegisterRequest;
import com.pos.backend.dto.employee.EmployeeResponse;
import com.pos.backend.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        AuthResponse authResponse = authService.authenticateUser(loginRequest);
        return ResponseEntity.ok(authResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<EmployeeResponse> registerUser(@Valid @RequestBody RegisterRequest registerRequest) {
        EmployeeResponse employeeResponse = authService.registerUser(registerRequest); // Assign to EmployeeResponse
        return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED); // Return 201 Created status
    }

    // Logout endpoint (sẽ được xử lý ở phía client bằng cách xóa token, hoặc thêm vào blacklist ở backend)
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        // Trong một ứng dụng JWT, logout thường được xử lý ở phía client bằng cách xóa token.
        // Tuy nhiên, nếu bạn muốn vô hiệu hóa token ở phía server (ví dụ: blacklist), bạn có thể implement logic ở đây.
        // Hiện tại, chúng ta chỉ trả về thông báo thành công.
        return ResponseEntity.ok("Logged out successfully");
    }

}