package com.pos.backend.service.Impl;

import com.pos.backend.model.Employee;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.service.base.EmployeeService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public Optional<Employee> findEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findEmployeeByEmail(String email) {
        return employeeRepository.findByEmail(email);
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

    @Override
    @Transactional(readOnly = true)
    public Set<String> getFinalPermissionsForEmployee(Long employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        Set<String> permissions = new HashSet<>();

        // 1️⃣ Quyền từ role
        if (employee.getRole() != null && employee.getRole().getPermissions() != null) {
            employee.getRole().getPermissions().forEach(p -> permissions.add(p.getName()));
        }

        // 2️⃣ Quyền riêng (nếu có)
        if (employee.getIndividualPermissions() != null) {
            employee.getIndividualPermissions().forEach(p -> permissions.add(p.getName()));
        }

        return permissions;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Employee> findAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteEmployeeById(Long id) {
        employeeRepository.deleteById(id);
    }
}
