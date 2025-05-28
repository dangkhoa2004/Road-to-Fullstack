/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.model;

/**
 * @author 04dkh
 */
import com.pos.backend.model.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

@Entity
@Table(name = "audit_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuditLog extends BaseEntity {

    public enum ActionType {
        INSERT, UPDATE, DELETE, LOGIN, LOGOUT, VIEW
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    @Column(name = "action_type", nullable = false, length = 10)
    private ActionType actionType;

    @Column(name = "table_name", nullable = false, length = 100)
    private String tableName;

    @Column(name = "record_id")
    private Long recordId;

    @Column(name = "action_time", nullable = false)
    private LocalDateTime actionTime = LocalDateTime.now();

    @Column(name = "old_data", columnDefinition = "JSON")
    private String oldData;

    @Column(name = "new_data", columnDefinition = "JSON")
    private String newData;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
}
