/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service;

/**
 *
 * @author 04dkh
 */
import com.pos.backend.dto.auth.LoginRequest;
import com.pos.backend.dto.auth.AuthResponse;
import com.pos.backend.dto.employee.EmployeeResponse;
import com.pos.backend.dto.role.RoleResponse;
import com.pos.backend.model.Employee;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder; // Để so sánh mật khẩu

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        // Authenticate user credentials
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Generate JWT token
        String jwt = jwtTokenProvider.generateToken(authentication);

        // Retrieve user details to return in response
        Employee employee = employeeRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + loginRequest.getUsername()));

        // Convert Employee entity to EmployeeResponse DTO
        EmployeeResponse employeeResponse = new EmployeeResponse(
                employee.getId(),
                employee.getName(),
                employee.getUsername(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getIsActive(),
                new RoleResponse(employee.getRole().getId(), employee.getRole().getName())
        );

        return new AuthResponse(jwt, "Login successful", employeeResponse);
    }
}
