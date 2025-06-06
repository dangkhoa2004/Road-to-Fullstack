package com.pos.backend.service.base;

import java.util.Set;

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
import com.pos.backend.model.Role;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.repository.RoleRepository;
import com.pos.backend.security.JwtTokenProvider;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmployeeService employeeService; // 👈 Thêm EmployeeService

    public AuthService(AuthenticationManager authenticationManager,
            JwtTokenProvider jwtTokenProvider,
            EmployeeRepository employeeRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            EmployeeService employeeService) { // 👈 Thêm EmployeeService
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.employeeService = employeeService; // 👈 Khởi tạo
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
}
