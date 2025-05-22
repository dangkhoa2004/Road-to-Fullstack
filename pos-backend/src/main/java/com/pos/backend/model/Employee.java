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

import jakarta.persistence.Column; // Đảm bảo import FetchType từ đây
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee extends BaseEntity {

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password_hash", nullable = false, length = 255) // Lưu trữ mật khẩu đã hash
    private String passwordHash;

    @ManyToOne(fetch = FetchType.EAGER) // <-- THAY ĐỔI TẠI ĐÂY: từ LAZY sang EAGER
    @JoinColumn(name = "role_id", nullable = false) // Khóa ngoại `role_id`
    private Role role;

    @Column(name = "phone", length = 20, unique = true)
    private String phone;

    @Column(name = "email", length = 100, unique = true)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true; // Mặc định là true
}