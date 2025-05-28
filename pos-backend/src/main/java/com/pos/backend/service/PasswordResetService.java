package com.pos.backend.service;

import com.pos.backend.model.Employee;
import com.pos.backend.model.PasswordResetToken;
import com.pos.backend.repository.EmployeeRepository;
import com.pos.backend.repository.PasswordResetTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class PasswordResetService {

    private final PasswordResetTokenRepository tokenRepository;
    private final EmployeeRepository employeeRepository;
    private final EmailService emailService;

    public PasswordResetService(PasswordResetTokenRepository tokenRepository, EmployeeRepository employeeRepository, EmailService emailService) {
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

    // Đã sửa đổi phương thức này
    public void sendPasswordResetEmail(Employee employee, String token, String appUrl) {
        String recipientAddress = employee.getEmail();
        String subject = "Đặt lại mật khẩu của bạn";

        // Thay vì gửi cả URL, bây giờ chỉ gửi token
        String message = "Xin chào " + employee.getName() + ",\n\nBạn đã yêu cầu đặt lại mật khẩu. Vui lòng sử dụng mã token sau để hoàn tất quá trình:\n\n" +
                "Mã Token: " + token +
                "\n\nMã token này sẽ hết hạn sau 24 giờ. Vui lòng nhập mã này vào ứng dụng của chúng tôi để đặt lại mật khẩu." +
                "\n\nNếu bạn không yêu cầu đặt lại mật khẩu, vui lòng bỏ qua email này." +
                "\n\nTrân trọng,\nHệ thống của bạn";

        emailService.sendEmail(recipientAddress, subject, message);
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