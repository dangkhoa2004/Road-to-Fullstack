package com.pos.backend.service.base;

import com.pos.backend.dto.auth.AuthResponse;
import com.pos.backend.dto.auth.LoginRequest;
import com.pos.backend.dto.auth.RegisterRequest;
import com.pos.backend.dto.employee.EmployeeResponse;
import com.pos.backend.dto.role.RoleResponse;
import com.pos.backend.model.Employee;
import com.pos.backend.model.Role;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.repository.RoleRepository;
import com.pos.backend.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmployeeRepository employeeRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        // Xác thực username/password
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        // Lấy thông tin nhân viên
        Employee employee = employeeRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginRequest.getUsername()));

        // Tạo RoleResponse từ Role của Employee
        RoleResponse roleResponse = new RoleResponse(employee.getRole().getId(), employee.getRole().getName());

        // Tạo EmployeeResponse
        EmployeeResponse employeeResponse = new EmployeeResponse(employee.getId(), employee.getName(), employee.getUsername(), roleResponse, employee.getPhone(), employee.getEmail(), employee.getIsActive());

        return new AuthResponse(jwt, "Login successful", employeeResponse);
    }

    @Transactional
    public EmployeeResponse registerUser(RegisterRequest registerRequest) {
        if (employeeRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        // Lấy role mặc định
        Role defaultRole = roleRepository.findByName("EMPLOYEE").orElseThrow(() -> new RuntimeException("Default role 'EMPLOYEE' not found. Please create it."));

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

        // Tạo RoleResponse từ Role của Employee đã lưu
        RoleResponse roleResponse = new RoleResponse(savedEmployee.getRole().getId(), savedEmployee.getRole().getName());

        // Tạo EmployeeResponse
        return new EmployeeResponse(savedEmployee.getId(), savedEmployee.getName(), savedEmployee.getUsername(), roleResponse, savedEmployee.getPhone(), savedEmployee.getEmail(), savedEmployee.getIsActive());
    }
}
