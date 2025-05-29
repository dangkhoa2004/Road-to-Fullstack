package com.pos.backend.service.base;

import com.pos.backend.dto.auth.AuthResponse;
import com.pos.backend.dto.auth.LoginRequest;
import com.pos.backend.dto.auth.RegisterRequest;
import com.pos.backend.dto.common.RoleDto;
import com.pos.backend.dto.employee.EmployeeResponse;
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

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
            EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtTokenProvider.generateToken(authentication);

        Employee employee = employeeRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginRequest.getUsername()));

        // Tạo RoleDto từ Role của Employee
        RoleDto roleDto = new RoleDto(employee.getRole().getId(), employee.getRole().getName());

        // Tạo EmployeeResponse với đúng thứ tự và kiểu dữ liệu
        EmployeeResponse employeeResponse = new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getUsername(),
                roleDto, // <-- Truyền RoleDto vào đây
                employee.getPhone(),
                employee.getEmail(),
                employee.getIsActive()
        // Không có tham số thừa ở đây nếu EmployeeResponse constructor có 7 tham số
        );

        return new AuthResponse(jwt, "Login successful", employeeResponse);
    }

    @Transactional
    public EmployeeResponse registerUser(RegisterRequest registerRequest) {
        if (employeeRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        Role defaultRole = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Default role 'EMPLOYEE' not found. Please create it."));

        Employee employee = new Employee();
        employee.setName(registerRequest.getName());
        employee.setUsername(registerRequest.getUsername());
        employee.setEmail(registerRequest.getEmail());
        employee.setPhone(registerRequest.getPhone());
        employee.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
        employee.setRole(defaultRole);
        employee.setIsActive(true);

        Employee savedEmployee = employeeRepository.save(employee);

        // Tạo RoleDto từ Role của Employee đã lưu
        RoleDto roleDto = new RoleDto(savedEmployee.getRole().getId(), savedEmployee.getRole().getName());

        // Tạo EmployeeResponse với đúng thứ tự và kiểu dữ liệu
        return new EmployeeResponse(
                savedEmployee.getId(),
                savedEmployee.getName(),
                savedEmployee.getUsername(),
                roleDto, // <-- Truyền RoleDto vào đây
                savedEmployee.getPhone(),
                savedEmployee.getEmail(),
                savedEmployee.getIsActive()
        // Không có tham số thừa ở đây
        );
    }
}
