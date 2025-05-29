package com.pos.backend.service.base;

import com.pos.backend.model.Employee;

import java.util.Optional;

public interface EmployeeService {
    Optional<Employee> findEmployeeById(Long id);

    Optional<Employee> findEmployeeByEmail(String email);

    Employee findEmployeeByUsername(String username);

    void saveEmployee(Employee employee);

    void changeEmployeePassword(Employee employee, String newPassword);
}