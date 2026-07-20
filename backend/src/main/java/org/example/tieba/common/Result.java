package org.example.tieba.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result<T> {
    private String code;
    private String message;
    private T data;

    public Result() {
    }

    public Result(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> Result<T> success() {
        return new Result<T>(ErrorCode.SUCCESS.getCode(), "OK");
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(ErrorCode.SUCCESS.getCode(), "OK", data);
    }

    public static <T> Result<T> success(String message, T data) {
        return new Result<T>(ErrorCode.SUCCESS.getCode(), message, data);
    }
}
