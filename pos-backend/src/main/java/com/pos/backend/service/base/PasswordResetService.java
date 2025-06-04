package com.pos.backend.service.base;

import com.pos.backend.model.Employee;
import com.pos.backend.model.PasswordResetToken;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.repository.PasswordResetTokenRepository;
import jakarta.mail.MessagingException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository, EmployeeRepository employeeRepository,
            EmailService emailService) {
        this.tokenRepository = tokenRepository;
        this.employeeRepository = employeeRepository;
        this.emailService = emailService;
    }

    @Transactional
    public String createPasswordResetTokenForEmployee(Employee employee) {
        Optional<PasswordResetToken> existingToken = tokenRepository.findByEmployee(employee);
        if (existingToken.isPresent()) {
            tokenRepository.delete(existingToken.get());
        }

        String token = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(token, employee);
        tokenRepository.save(myToken);
        return token;
    }

    public String validatePasswordResetToken(String token) {
        final Optional<PasswordResetToken> passToken = tokenRepository.findByToken(token);

        if (passToken.isEmpty()) {
            return "invalidToken";
        }

        PasswordResetToken resetToken = passToken.get();

        if (resetToken.getExpiryDate().before(new Date())) {
            tokenRepository.delete(resetToken);
            return "expired";
        }

        return null;
    }

    public Employee getEmployeeByPasswordResetToken(String token) {
        return tokenRepository.findByToken(token)
                .map(PasswordResetToken::getEmployee)
                .orElse(null);
    }

    // Đã sửa đổi để sử dụng lại template HTML và truyền token
    public void sendPasswordResetEmail(Employee employee, String token, String appUrl) throws MessagingException {
        String recipientAddress = employee.getEmail();
        String subject = "Đặt lại mật khẩu của bạn";

        Map<String, Object> templateVariables = new HashMap<>();
        templateVariables.put("employeeName", employee.getName());
        templateVariables.put("token", token); // Đảm bảo truyền token vào template variables
        String resetUrl = appUrl + "/reset-password?token=" + token; // Giữ lại nếu bạn cần resetUrl trong template
        templateVariables.put("resetUrl", resetUrl); // Có thể bỏ qua nếu template không dùng resetUrl

        // Các biến khác cho template HTML của bạn
        templateVariables.put("logoUrl", "https://i.ibb.co/7txFXRCM/banner.jpg"); // Thay đổi URL logo nếu cần
        templateVariables.put("systemName", "Hệ thống Quản lý POS"); // Thay đổi tên hệ thống
        templateVariables.put("companyName", "Tên Công ty của bạn"); // Thay đổi tên công ty

        // Gọi phương thức sendHtmlEmail để sử dụng template
        emailService.sendHtmlEmail(recipientAddress, subject, "password_reset_email", templateVariables);
    }

    @Transactional
    public void deleteToken(String token) {
        tokenRepository.deleteByToken(token);
    }

    @Transactional
    public void deleteAllTokensForEmployee(Employee employee) {
        tokenRepository.deleteByEmployee(employee);
    }
}