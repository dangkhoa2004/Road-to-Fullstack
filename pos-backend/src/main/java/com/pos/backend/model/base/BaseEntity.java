/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.model.base;

/**
 *
 * @author 04dkh
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass // Cho phép lớp này được kế thừa bởi các Entity khác
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp // Tự động thiết lập thời gian khi bản ghi được tạo
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Tự động cập nhật thời gian khi bản ghi được sửa đổi
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version // Cho Optimistic Locking
    private Long version;
}
