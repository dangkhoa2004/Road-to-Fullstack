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

@Entity
@Table(name = "tables")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tables extends BaseEntity {

    public enum TableStatus {
        available, occupied, reserved, cleaning, out_of_service
    }

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20) // ENUM max length is 20 for 'out_of_service'
    private TableStatus status = TableStatus.available;

    @Column(name = "capacity", nullable = false)
    private Integer capacity = 0;
}