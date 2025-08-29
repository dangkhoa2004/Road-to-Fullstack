/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.controller;

/**
 * @author 04dkh
 */

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pos.backend.dto.common.ApiResponse;
import com.pos.backend.dto.employee.EmployeeRequest;
import com.pos.backend.dto.employee.EmployeeResponse;
import com.pos.backend.model.Employee;
import com.pos.backend.model.Role;
import com.pos.backend.service.base.EmployeeService;
import com.pos.backend.service.base.RoleService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;
    private final RoleService roleService;

    public EmployeeController(EmployeeService employeeService, RoleService roleService) {
        this.employeeService = employeeService;
        this.roleService = roleService;
    }

    @GetMapping("/list")
    public ResponseEntity<ApiResponse<List<EmployeeResponse>>> getAllEmployees() {
        List<Employee> employees = employeeService.findAllEmployees();

        // Tạo danh sách response kèm quyền đầy đủ
        List<EmployeeResponse> responses = employees.stream()
                .map(emp -> new EmployeeResponse(emp, employeeService.getFinalPermissionsForEmployee(emp.getId())))
                .toList();

        return ResponseEntity.ok(new ApiResponse<>("Danh sách nhân viên", "200", responses));
    }

    @GetMapping("/by-email")
    public ResponseEntity<ApiResponse<EmployeeResponse>> findEmployeeByEmail(@RequestParam String email) {
        Optional<Employee> optionalEmployee = employeeService.findEmployeeByEmail(email);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Set<String> finalPermissions = employeeService.getFinalPermissionsForEmployee(employee.getId());
            EmployeeResponse response = new EmployeeResponse(employee, finalPermissions);
            return ResponseEntity.ok(new ApiResponse<>("Tìm thấy nhân viên", "200", response));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Không tìm thấy nhân viên với email này", "404", null));
    }

    @GetMapping("/by-username")
    public ResponseEntity<ApiResponse<EmployeeResponse>> findEmployeeByUsername(@RequestParam String username) {
        Employee employee = employeeService.findEmployeeByUsername(username);
        if (employee != null) {
            Set<String> finalPermissions = employeeService.getFinalPermissionsForEmployee(employee.getId());
            EmployeeResponse response = new EmployeeResponse(employee, finalPermissions);
            return ResponseEntity.ok(new ApiResponse<>("Tìm thấy nhân viên", "200", response));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Không tìm thấy nhân viên với username này", "404", null));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> getEmployeeById(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeService.findEmployeeById(id);
        if (optionalEmployee.isPresent()) {
            Employee employee = optionalEmployee.get();
            Set<String> finalPermissions = employeeService.getFinalPermissionsForEmployee(employee.getId());
            EmployeeResponse response = new EmployeeResponse(employee, finalPermissions);
            return ResponseEntity.ok(new ApiResponse<>("Tìm thấy nhân viên", "200", response));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>("Không tìm thấy nhân viên với ID này", "404", null));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeResponse>> saveEmployee(@Valid @RequestBody EmployeeRequest request) {
        Optional<Role> optionalRole = roleService.findRoleById(request.getRoleId());
        if (optionalRole.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>("Không tìm thấy vai trò", "400", null));
        }

        Employee employee = new Employee();
        employee.setName(request.getName());
        employee.setUsername(request.getUsername());
        employee.setPasswordHash(request.getPassword()); // Sẽ hash trong service
        employee.setRole(optionalRole.get());
        employee.setPhone(request.getPhone());
        employee.setEmail(request.getEmail());
        employee.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        employeeService.saveEmployee(employee);
        EmployeeResponse response = new EmployeeResponse(employee);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Nhân viên đã được lưu", "201", response));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeResponse>> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequest request) {
        Optional<Employee> optionalEmployee = employeeService.findEmployeeById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Không tìm thấy nhân viên với ID này", "404", null));
        }

        Employee employee = optionalEmployee.get();
        employee.setName(request.getName());
        employee.setUsername(request.getUsername());
        employee.setPhone(request.getPhone());
        employee.setEmail(request.getEmail());
        employee.setIsActive(request.getIsActive() != null ? request.getIsActive() : true);

        if (request.getRoleId() != null) {
            Optional<Role> optionalRole = roleService.findRoleById(request.getRoleId());
            optionalRole.ifPresent(employee::setRole);
        }

        employeeService.saveEmployee(employee);
        EmployeeResponse response = new EmployeeResponse(employee);
        return ResponseEntity.ok(new ApiResponse<>("Cập nhật thông tin nhân viên thành công", "200", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEmployee(@PathVariable Long id) {
        Optional<Employee> optionalEmployee = employeeService.findEmployeeById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Không tìm thấy nhân viên với ID này", "404", null));
        }

        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok(new ApiResponse<>("Xoá nhân viên thành công", "200", "Employee deleted successfully"));
    }

    @PostMapping("/{id}/change-password")
    public ResponseEntity<ApiResponse<String>> changeEmployeePassword(
            @PathVariable Long id,
            @RequestParam String newPassword) {

        Optional<Employee> optionalEmployee = employeeService.findEmployeeById(id);
        if (optionalEmployee.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>("Không tìm thấy nhân viên với ID này", "404", null));
        }

        Employee employee = optionalEmployee.get();
        employeeService.changeEmployeePassword(employee, newPassword);
        return ResponseEntity.ok(new ApiResponse<>("Đổi mật khẩu thành công", "200", "Password changed successfully"));
    }
}
