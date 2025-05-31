package com.pos.backend.service.base;

import com.pos.backend.model.Employee;

import java.util.Optional;
import java.util.Set;

public interface EmployeeService {
    Optional<Employee> findEmployeeById(Long id);

    Optional<Employee> findEmployeeByEmail(String email);

    Employee findEmployeeByUsername(String username);

    void saveEmployee(Employee employee);

    void changeEmployeePassword(Employee employee, String newPassword);

    Set<String> getFinalPermissionsForEmployee(Long employeeId);

}