package com.pos.backend.service.base;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pos.backend.dto.auth.AuthResponse;
import com.pos.backend.dto.auth.LoginRequest;
import com.pos.backend.dto.auth.RegisterRequest;
import com.pos.backend.dto.employee.EmployeeResponse;
import com.pos.backend.model.Employee;
import com.pos.backend.model.PasswordResetToken;
import com.pos.backend.model.Role;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.repository.PasswordResetTokenRepository;
import com.pos.backend.repository.RoleRepository;
import com.pos.backend.security.JwtTokenProvider;

import jakarta.mail.MessagingException;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtTokenProvider jwtTokenProvider,
                       EmployeeRepository employeeRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       EmployeeService employeeService,
                    PasswordResetTokenRepository tokenRepository,
                        EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeService = employeeService;
        this.tokenRepository = tokenRepository;
        this.emailService = emailService;
    }

    /**
     * Xác thực người dùng
     */
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        // Xác thực username/password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Sinh token
        String jwt = jwtTokenProvider.generateToken(authentication);

        // Lấy thông tin nhân viên
        Employee employee = employeeRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException(
                        "User not found with username: " + loginRequest.getUsername()));

        // Lấy quyền cuối cùng (role + individual)
        Set<String> finalPermissions = employeeService.getFinalPermissionsForEmployee(employee.getId());

        // Tạo EmployeeResponse đầy đủ (có quyền)
        EmployeeResponse employeeResponse = new EmployeeResponse(employee, finalPermissions);

        return new AuthResponse(jwt, "Login successful", employee.getId());
    }

    /**
     * Đăng ký người dùng mới
     */
    @Transactional
    public EmployeeResponse registerUser(RegisterRequest registerRequest) {
        if (employeeRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        // Lấy role mặc định
        Role defaultRole = roleRepository.findByName("Nhân viên")
                .orElseThrow(() -> new RuntimeException("Default role 'Nhân viên' not found. Please create it."));

        // Tạo nhân viên mới
        Employee employee = new Employee();
        employee.setName(registerRequest.getName());
        employee.setUsername(registerRequest.getUsername());
        employee.setEmail(registerRequest.getEmail());
        employee.setPhone(registerRequest.getPhone());
        employee.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        employee.setRole(defaultRole);
        employee.setIsActive(true);

        Employee savedEmployee = employeeRepository.save(employee);

        // Không có quyền riêng lúc đăng ký, chỉ trả về thông tin cơ bản
        return new EmployeeResponse(savedEmployee);
    }

        @Transactional
    public String createPasswordResetTokenForEmployee(Employee employee) {
        Optional<PasswordResetToken> existingToken = tokenRepository.findByEmployee(employee);
        if (existingToken.isPresent()) {
            tokenRepository.delete(existingToken.get());
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, employee);
        tokenRepository.save(myToken);
        return token;
    }

    public String validatePasswordResetToken(String token) {
        final Optional<PasswordResetToken> passToken = tokenRepository.findByToken(token);

        if (passToken.isEmpty()) {
            return "invalidToken";
        }

        PasswordResetToken resetToken = passToken.get();

        if (resetToken.getExpiryDate().before(new Date())) {
            tokenRepository.delete(resetToken);
            return "expired";
        }

        return null;
    }

    public Employee getEmployeeByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token)
                .map(PasswordResetToken::getEmployee)
                .orElse(null);
    }

    // Đã sửa đổi để sử dụng lại template HTML và truyền token
    public void sendPasswordResetEmail(Employee employee, String token, String appUrl) throws MessagingException {
        String recipientAddress = employee.getEmail();
        String subject = "Đặt lại mật khẩu của bạn";

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("employeeName", employee.getName());
        templateVariables.put("token", token); // Đảm bảo truyền token vào template variables
        String resetUrl = appUrl + "/reset-password?token=" + token; // Giữ lại nếu bạn cần resetUrl trong template
        templateVariables.put("resetUrl", resetUrl); // Có thể bỏ qua nếu template không dùng resetUrl

        // Các biến khác cho template HTML của bạn
        templateVariables.put("logoUrl", "https://i.ibb.co/7txFXRCM/banner.jpg"); // Thay đổi URL logo nếu cần
        templateVariables.put("systemName", "Hệ thống Quản lý POS"); // Thay đổi tên hệ thống
        templateVariables.put("companyName", "Tên Công ty của bạn"); // Thay đổi tên công ty

        // Gọi phương thức sendHtmlEmail để sử dụng template
        emailService.sendHtmlEmail(recipientAddress, subject, "password_reset_email", templateVariables);
    }

    @Transactional
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Transactional
    public void deleteAllTokensForEmployee(Employee employee) {
        tokenRepository.deleteByEmployee(employee);
    }
}
