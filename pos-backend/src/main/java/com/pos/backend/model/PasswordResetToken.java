package com.pos.backend.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "password_reset_tokens")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetToken {
    private static final int EXPIRATION = 60 * 24; // Token hết hạn sau 24 giờ

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) // Token phải duy nhất
    private String token;

    // Thay đổi từ User thành Employee
    @OneToOne(targetEntity = Employee.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "employee_id") // Sử dụng employee_id
    private Employee employee; // Đổi tên trường thành employee

    @Column(nullable = false)
    private Date expiryDate;

    public PasswordResetToken(String token, Employee employee) {
        this.token = token;
        this.employee = employee;
        this.expiryDate = calculateExpiryDate();
    }

    private Date calculateExpiryDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION * 60 * 1000);
    }
}
