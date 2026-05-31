package org.example.tieba.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.tieba.common.Result;
import org.example.tieba.constants.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<String>> handleBusiness(BusinessException e) {
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(new Result<>(e.getErrorCode().getCode(), e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<String>> handleValidation(MethodArgumentNotValidException ex) {
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
                new Result<>(ErrorCode.INVALID_PARAMETER.getCode(), msg)
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public Result<String> handleNotFound(NoResourceFoundException ex) {
        return new Result<>(ErrorCode.RESOURCE_NOT_FOUND.getCode(), "资源不存在");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Result<String>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new Result<>(ErrorCode.BAD_REQUEST.getCode(), ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleOther(Exception e) {
        log.error("系统错误", e);
        return ResponseEntity.internalServerError().body(
                new Result<>(ErrorCode.INTERNAL_SERVER_ERROR.getCode(), "服务器错误")
        );
    }
}
