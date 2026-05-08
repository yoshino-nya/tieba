package org.example.tieba.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.example.tieba.constants.ErrorCodeConstants;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiResponse<T>(String code, String message, T data) {


    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(ErrorCodeConstants.SUCCESS, "ok", data);
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(ErrorCodeConstants.SUCCESS, "ok", null);
    }

    public static <T> ApiResponse<T> error(String code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}
