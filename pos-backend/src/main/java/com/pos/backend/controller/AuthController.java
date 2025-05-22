/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 *
 * @author 04dkh
 */
import com.pos.backend.dto.auth.LoginRequest;
import com.pos.backend.dto.auth.AuthResponse;
import com.pos.backend.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
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

    // Logout endpoint (sẽ được xử lý ở phía client bằng cách xóa token, hoặc thêm vào blacklist ở backend)
    @PostMapping("/logout")
    public ResponseEntity<String> logoutUser() {
        // Trong một ứng dụng JWT, logout thường được xử lý ở phía client bằng cách xóa token.
        // Tuy nhiên, nếu bạn muốn vô hiệu hóa token ở phía server (ví dụ: blacklist), bạn có thể implement logic ở đây.
        // Hiện tại, chúng ta chỉ trả về thông báo thành công.
        return ResponseEntity.ok("Logged out successfully");
    }

    // Endpoint để lấy thông tin người dùng hiện tại (sẽ cần Spring Security để lấy Principal)
    // Sẽ được thêm vào sau khi cấu hình Spring Security hoàn chỉnh
    // @GetMapping("/me")
    // public ResponseEntity<EmployeeResponse> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    //    // Logic để lấy thông tin người dùng từ userDetails và trả về DTO
    // }
}
