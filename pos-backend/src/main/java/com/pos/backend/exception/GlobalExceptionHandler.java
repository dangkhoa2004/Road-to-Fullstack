package com.pos.backend.exception;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import com.pos.backend.dto.common.ApiResponse;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * Tổng hợp handler cho nhiều lỗi thuộc nhóm 4xx (client) & 5xx (server).
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /* ===================== 4xx: Client Errors ===================== */

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotFound(NoSuchElementException ex) {
        return build(HttpStatus.NOT_FOUND, ex.getMessage(), "404");
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadRequest(IllegalArgumentException ex) {
        return build(HttpStatus.BAD_REQUEST, ex.getMessage(), "400");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .collect(Collectors.toMap(
                fe -> fe.getField(),
                fe -> fe.getDefaultMessage() == null ? "Invalid" : fe.getDefaultMessage(),
                (a, b) -> a)); // giữ lỗi đầu tiên nếu trùng field
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse<>("Dữ liệu không hợp lệ.", "400", errors));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleConstraintViolation(ConstraintViolationException ex) {
        Map<String, String> errors = new HashMap<>();
        for (ConstraintViolation<?> v : ex.getConstraintViolations()) {
            errors.put(v.getPropertyPath().toString(), v.getMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(new ApiResponse<>("Dữ liệu không hợp lệ.", "400", errors));
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingParam(MissingServletRequestParameterException ex) {
        String msg = "Thiếu tham số: " + ex.getParameterName();
        return build(HttpStatus.BAD_REQUEST, msg, "400");
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingPathVar(MissingPathVariableException ex) {
        String msg = "Thiếu path variable: " + ex.getVariableName();
        return build(HttpStatus.BAD_REQUEST, msg, "400");
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ApiResponse<Void>> handleMissingHeader(MissingRequestHeaderException ex) {
        String msg = "Thiếu header: " + ex.getHeaderName();
        return build(HttpStatus.BAD_REQUEST, msg, "400");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotReadable(HttpMessageNotReadableException ex) {
        return build(HttpStatus.BAD_REQUEST, "Payload không hợp lệ hoặc không đọc được.", "400");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMethodNotSupported(HttpRequestMethodNotSupportedException ex) {
        String msg = "HTTP method không được hỗ trợ: " + ex.getMethod();
        return build(HttpStatus.METHOD_NOT_ALLOWED, msg, "405");
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ApiResponse<Void>> handleMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex) {
        String msg = "Content-Type không được hỗ trợ.";
        return build(HttpStatus.UNSUPPORTED_MEDIA_TYPE, msg, "415");
    }

    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    public ResponseEntity<ApiResponse<Void>> handleNotAcceptable(HttpMediaTypeNotAcceptableException ex) {
        String msg = "Không có representation phù hợp với Accept header.";
        return build(HttpStatus.NOT_ACCEPTABLE, msg, "406");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Void>> handleConflict(DataIntegrityViolationException ex) {
        return build(HttpStatus.CONFLICT, "Dữ liệu không hợp lệ hoặc vi phạm ràng buộc.", "409");
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ApiResponse<Void>> handleDuplicateKey(DuplicateKeyException ex) {
        return build(HttpStatus.CONFLICT, "Giá trị đã tồn tại (trùng khóa).", "409");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDenied(AccessDeniedException ex) {
        return build(HttpStatus.FORBIDDEN, "Bạn không có quyền truy cập tài nguyên này.", "403");
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleUserNotFound(UsernameNotFoundException ex) {
        return build(HttpStatus.UNAUTHORIZED, "Tên đăng nhập hoặc mật khẩu không đúng.", "401");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiResponse<Void>> handleBadCredentials(BadCredentialsException ex) {
        return build(HttpStatus.UNAUTHORIZED, "Tên đăng nhập hoặc mật khẩu không đúng.", "401");
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ApiResponse<Void>> handleDisabled(DisabledException ex) {
        return build(HttpStatus.FORBIDDEN, "Tài khoản đã bị vô hiệu hóa.", "403");
    }

    @ExceptionHandler(LockedException.class)
    public ResponseEntity<ApiResponse<Void>> handleLocked(LockedException ex) {
        return build(HttpStatus.LOCKED, "Tài khoản đã bị khóa.", "423");
    }

    /* ===================== 5xx: Server Errors ===================== */

    @ExceptionHandler(OptimisticLockingFailureException.class)
    public ResponseEntity<ApiResponse<Void>> handleOptimisticLock(OptimisticLockingFailureException ex) {
        return build(HttpStatus.CONFLICT, "Xung đột phiên bản dữ liệu. Vui lòng thử lại.", "409");
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiResponse<Void>> handleResponseStatus(ResponseStatusException ex) {
        HttpStatusCode status = ex.getStatusCode();
        String code = String.valueOf(status.value());
        String message = ex.getReason() != null ? ex.getReason() : "Lỗi.";
        return ResponseEntity.status(status.value()).body(new ApiResponse<>(message, code, null));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntime(RuntimeException ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), "500");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleInternalError(Exception ex) {
        return build(HttpStatus.INTERNAL_SERVER_ERROR, "Lỗi server: " + ex.getMessage(), "500");
    }

    /* ===================== Helpers ===================== */

    private <T> ResponseEntity<ApiResponse<T>> build(HttpStatus status, String message, String code) {
        return ResponseEntity.status(status).body(new ApiResponse<>(message, code, null));
    }
}
