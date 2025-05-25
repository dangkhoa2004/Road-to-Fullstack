/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.service;

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

    public AuthService(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider,
                       EmployeeRepository employeeRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.employeeRepository = employeeRepository;
        this.roleRepository = roleRepository;
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

    @Transactional
    public EmployeeResponse registerUser(RegisterRequest registerRequest) {
        // Check if username is already taken
        if (employeeRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Username is already taken!");
        }

        // Check if email is already taken (optional, but good practice)
//        if (employeeRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email is already registered!");
//        }

        // Find the default role (e.g., "USER" or "EMPLOYEE")
        Role defaultRole = roleRepository.findByName("EMPLOYEE")
                .orElseThrow(() -> new RuntimeException("Default role 'EMPLOYEE' not found. Please create it."));

        // Create new employee's account
        Employee employee = new Employee();
        employee.setName(registerRequest.getName());
        employee.setUsername(registerRequest.getUsername());
        employee.setEmail(registerRequest.getEmail());
        employee.setPhone(registerRequest.getPhone());
        employee.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword())); // <--- CHANGE THIS LINE
        employee.setRole(defaultRole); // Assign the default role
        employee.setIsActive(true); // Set initial active status

        Employee savedEmployee = employeeRepository.save(employee);

        return new EmployeeResponse(
                savedEmployee.getId(),
                savedEmployee.getName(),
                savedEmployee.getUsername(),
                savedEmployee.getPhone(),
                savedEmployee.getEmail(),
                savedEmployee.getIsActive(),
                new RoleResponse(savedEmployee.getRole().getId(), savedEmployee.getRole().getName())
        );
    }
}
