/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.security;

/**
 *
 * @author 04dkh
 */
import com.pos.backend.model.Employee;
import com.pos.backend.repository.EmployeeRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final EmployeeRepository employeeRepository;

    public CustomUserDetailsService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Employee employee = employeeRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));

        // Kiểm tra xem tài khoản có hoạt động không
        if (!employee.getIsActive()) {
            throw new UsernameNotFoundException("User account is disabled: " + username);
        }

        // Chuyển đổi vai trò của Employee thành GrantedAuthority của Spring Security
        // Prefix "ROLE_" là quy ước của Spring Security cho vai trò
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + employee.getRole().getName().toUpperCase())
        );

        return new org.springframework.security.core.userdetails.User(
                employee.getUsername(),
                employee.getPasswordHash(), // Mật khẩu đã hash từ CSDL
                authorities
        );
    }
}
