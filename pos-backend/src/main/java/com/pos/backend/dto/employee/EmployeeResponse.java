package com.pos.backend.dto.employee;

import com.pos.backend.dto.role.RoleResponse;
import com.pos.backend.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * DTO trả về cho Employee, bao gồm cả Role và danh sách quyền cuối cùng (role + riêng).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponse {

    private Long id;
    private String name;
    private String username;
    private String phone;
    private String email;
    private Boolean isActive;

    private RoleResponse role; // Thông tin role

    private Set<String> permissions; // Danh sách quyền cuối cùng (tên quyền)

    /**
     * Constructor từ entity Employee (không có danh sách quyền)
     * – Dùng khi chỉ muốn thông tin cơ bản.
     */
    public EmployeeResponse(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.username = employee.getUsername();
        this.phone = employee.getPhone();
        this.email = employee.getEmail();
        this.isActive = employee.getIsActive();

        if (employee.getRole() != null) {
            this.role = new RoleResponse(employee.getRole());
        }
    }

    /**
     * Constructor từ entity Employee + danh sách quyền cuối cùng.
     * – Dùng khi muốn trả về cả danh sách quyền (role + quyền riêng).
     */
    public EmployeeResponse(Employee employee, Set<String> finalPermissions) {
        this(employee); // Gọi constructor cơ bản trước
        this.permissions = finalPermissions;
    }
}
