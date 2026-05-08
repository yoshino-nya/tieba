package org.example.tieba.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.tieba.constants.ErrorCodeConstants;
import org.example.tieba.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusiness(BusinessException e) {
        return ResponseEntity.badRequest().body(
                ApiResponse.error(e.getCode(), e.getMessage())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<String>> handleValidation(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<String> errorMessages = new ArrayList<>();

        // 字段级错误
        for (FieldError fieldError : bindingResult.getFieldErrors()) {
            errorMessages.add(fieldError.getField() + ": " + fieldError.getDefaultMessage());
        }

        // 对象级错误（例如 @AtLeastOneNotBlank）
        for (ObjectError objectError : bindingResult.getGlobalErrors()) {
            errorMessages.add(objectError.getDefaultMessage());
        }

        String msg = String.join(", ", errorMessages);
        return ResponseEntity.badRequest().body(
                ApiResponse.error("INVALID_PARAMETER", msg)
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ApiResponse<String>> handleNotFound(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                ApiResponse.error(ErrorCodeConstants.NOT_FOUND, "资源不存在")
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleOther(Exception e) {
        log.error("系统错误", e);
        return ResponseEntity.internalServerError().body(
                ApiResponse.error("INTERNAL", "服务器错误")
        );
    }
}
