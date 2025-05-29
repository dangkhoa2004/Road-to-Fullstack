package com.pos.backend.service.base;

import com.pos.backend.model.Employee;
import java.util.Optional; // Import Optional

public interface EmployeeService {

    Optional<Employee> findEmployeeByEmail(String email);
    Employee findEmployeeByUsername(String username);
    void saveEmployee(Employee employee);
    void changeEmployeePassword(Employee employee, String newPassword);
}