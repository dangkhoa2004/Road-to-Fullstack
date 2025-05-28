package com.pos.backend.repository;

import com.pos.backend.model.Employee;
import com.pos.backend.model.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    Optional<PasswordResetToken> findByToken(String token);

    Optional<PasswordResetToken> findByEmployee(Employee employee);

    void deleteByToken(String token);

    void deleteByEmployee(Employee employee);
}