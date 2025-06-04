package com.pos.backend.controller;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.dto.auth.AuthResponse;
import com.pos.backend.dto.auth.LoginRequest;
import com.pos.backend.dto.auth.RegisterRequest;
import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.employee.EmployeeResponse;
import com.pos.backend.dto.reset_password.ForgotPasswordRequest;
import com.pos.backend.dto.reset_password.ResetPasswordRequest;
import com.pos.backend.dto.reset_password.ResetPasswordResponse;
import com.pos.backend.model.Employee;
import com.pos.backend.service.base.AuthService;
import com.pos.backend.service.base.EmployeeService;
import com.pos.backend.service.base.PasswordResetService;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final EmployeeService employeeService;
    private final PasswordResetService passwordResetService;

    public AuthController(AuthService authService, EmployeeService employeeService,
            PasswordResetService passwordResetService) {
        this.authService = authService;
        this.employeeService = employeeService;
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            AuthResponse authResponse = authService.authenticateUser(loginRequest);
            ApiResponse<AuthResponse> apiResponse = new ApiResponse<>("Đăng nhập thành công", "200", authResponse);
            return ResponseEntity.ok(apiResponse);
        } catch (UsernameNotFoundException | BadCredentialsException e) {
            ApiResponse<AuthResponse> errorResponse = new ApiResponse<>("Tên đăng nhập hoặc mật khẩu không đúng.",
                    "401", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            ApiResponse<AuthResponse> errorResponse = new ApiResponse<>("Đăng nhập thất bại: " + e.getMessage(), "500",
                    null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<EmployeeResponse>> registerUser(
            @Valid @RequestBody RegisterRequest registerRequest) {
        try {
            EmployeeResponse employeeResponse = authService.registerUser(registerRequest);
            ApiResponse<EmployeeResponse> apiResponse = new ApiResponse<>("Đăng ký người dùng thành công", "201",
                    employeeResponse);
            return new ResponseEntity<>(apiResponse, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            ApiResponse<EmployeeResponse> errorResponse = new ApiResponse<>(e.getMessage(), "400", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        } catch (DataIntegrityViolationException e) {
            ApiResponse<EmployeeResponse> errorResponse = new ApiResponse<>(
                    "Tên đăng nhập, Email hoặc Số điện thoại đã tồn tại.", "409", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
        } catch (RuntimeException e) {
            ApiResponse<EmployeeResponse> errorResponse = new ApiResponse<>("Đăng ký thất bại: " + e.getMessage(),
                    "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            ApiResponse<EmployeeResponse> errorResponse = new ApiResponse<>("Đăng ký thất bại: " + e.getMessage(),
                    "500", null);
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<String>> logoutUser() {
        ApiResponse<String> apiResponse = new ApiResponse<>("Đăng xuất thành công", "200", "Logged out successfully");
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<ApiResponse<ResetPasswordResponse>> forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest requestBody, HttpServletRequest request) {
        String employeeEmail = requestBody.getEmail();
        Optional<Employee> employeeOptional = employeeService.findEmployeeByEmail(employeeEmail);
        if (employeeOptional.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(
                    "Nếu email của bạn tồn tại trong hệ thống, chúng tôi sẽ gửi một liên kết đặt lại mật khẩu.", "200",
                    new ResetPasswordResponse("Email not found, but generic message sent.", true)));
        }
        Employee employee = employeeOptional.get();
        String token = passwordResetService.createPasswordResetTokenForEmployee(employee);
        String appUrl = request.getScheme() + "://" + request.getServerName();
        if (request.getServerPort() != 80 && request.getServerPort() != 443) {
            appUrl += ":" + request.getServerPort();
        }
        try {
            passwordResetService.sendPasswordResetEmail(employee, token, appUrl);
        } catch (MessagingException e) {
            System.err.println("Error sending password reset email: " + e.getMessage());
            return ResponseEntity.status(500)
                    .body(new ApiResponse<>("Có lỗi xảy ra khi gửi email đặt lại mật khẩu. Vui lòng thử lại sau.",
                            "500", new ResetPasswordResponse("Failed to send password reset email.", false)));
        }
        return ResponseEntity.ok(new ApiResponse<>(
                "Nếu email của bạn tồn tại trong hệ thống, chúng tôi sẽ gửi một liên kết đặt lại mật khẩu.", "200",
                new ResetPasswordResponse("Password reset email sent.", true)));
    }

    @PostMapping("/reset-password")
    public ResponseEntity<ApiResponse<ResetPasswordResponse>> resetPassword(@RequestParam("token") String token,
            @Valid @RequestBody ResetPasswordRequest requestBody) {
        if (token == null || token.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Token không được để trống.", "400",
                            new ResetPasswordResponse("Token is empty.", false)));
        }

        if (!requestBody.getNewPassword().equals(requestBody.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Mật khẩu mới và xác nhận mật khẩu không khớp.", "400",
                            new ResetPasswordResponse("New password and confirm password do not match.", false)));
        }

        String validationResult = passwordResetService.validatePasswordResetToken(token);
        if (validationResult != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Token không hợp lệ hoặc đã hết hạn: " + validationResult, "400",
                            new ResetPasswordResponse("Invalid or expired token.", false)));
        }

        Employee employee = passwordResetService.getEmployeeByPasswordResetToken(token);
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Không tìm thấy người dùng cho token này.", "404",
                            new ResetPasswordResponse("User not found for this token.", false)));
        }

        employeeService.changeEmployeePassword(employee, requestBody.getNewPassword());
        passwordResetService.deleteToken(token);

        return ResponseEntity.ok(new ApiResponse<>("Mật khẩu của bạn đã được đặt lại thành công.", "200",
                new ResetPasswordResponse("Password reset successfully.", true)));
    }
}