/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.model;

/**
 *
 * @author 04dkh
 */
import com.pos.backend.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Set; // Dùng Set để tránh trùng lặp

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BaseEntity {

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    // Mối quan hệ One-to-Many với Employee
    // mappedBy trỏ đến trường 'role' trong lớp Employee
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Employee> employees; // Sử dụng Set để tránh trùng lặp

    // Có thể thêm quyền hạn chi tiết cho từng vai trò tại đây hoặc trong một bảng khác
    // Ví dụ: @Column(name = "permissions", columnDefinition = "JSON")
    // private String permissions;
}
