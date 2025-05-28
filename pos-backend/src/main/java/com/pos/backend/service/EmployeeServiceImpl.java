package com.pos.backend.service;

import com.pos.backend.model.Employee;
import com.pos.backend.repository.EmployeeRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional; // Import Optional

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional(readOnly = true)
    // Thay đổi kiểu trả về thành Optional<Employee>
    public Optional<Employee> findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email); // Trả về Optional trực tiếp
    }

    @Override
    @Transactional(readOnly = true)
    public Employee findEmployeeByUsername(String username) {
        return employeeRepository.findByUsername(username).orElse(null);
    }

    @Override
    @Transactional
    public void saveEmployee(Employee employee) {
        employeeRepository.save(employee);
    }

    @Override
    @Transactional
    public void changeEmployeePassword(Employee employee, String newPassword) {
        employee.setPasswordHash(passwordEncoder.encode(newPassword));
        employeeRepository.save(employee);
    }
}