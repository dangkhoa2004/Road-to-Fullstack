/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pos.backend.model;

/**
 * @author 04dkh
 */

import com.pos.backend.model.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemSetting extends BaseEntity {

    @Id // Setting table uses a fixed ID (1)
    private Long id = 1L; // Assuming a single row with ID 1 for settings

    @Column(name = "store_name", nullable = false, length = 100)
    private String storeName;

    @Column(name = "address", columnDefinition = "TEXT")
    private String address;

    @Column(name = "phone", length = 20)
    private String phone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "logo_path", columnDefinition = "TEXT")
    private String logoPath;

    @Column(name = "tax_rate", nullable = false, precision = 5, scale = 2)
    private BigDecimal taxRate = BigDecimal.ZERO;

    @Column(name = "currency", nullable = false, length = 10)
    private String currency = "VND";

    @Column(name = "invoice_prefix", length = 20)
    private String invoicePrefix;

    @Column(name = "printer_name", length = 100)
    private String printerName;

    @Column(name = "default_language", nullable = false, length = 10)
    private String defaultLanguage = "VI";

    @Column(name = "backup_path", columnDefinition = "TEXT")
    private String backupPath;
}
