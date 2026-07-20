package org.example.tieba.common;

import lombok.Getter;

@Getter
public enum ErrorCode {

    // 成功
    SUCCESS("SUCCESS", 200), // 200 OK

    // 资源相关
    RESOURCE_ALREADY_EXISTS("RESOURCE_ALREADY_EXISTS", 409), // 409 Conflict
    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", 404), // 404 Not Found
    TOO_MANY_REQUESTS("TOO_MANY_REQUESTS",429),

    // 用户相关
    USER_NOT_FOUND("USER_NOT_FOUND", 404), // 404 Not Found
    INVALID_USER_CREDENTIALS("INVALID_USER_CREDENTIALS", 401), // 401 Unauthorized

    // 参数相关
    INVALID_PARAMETER("INVALID_PARAMETER", 400), // 400 Bad Request
    MISSING_REQUIRED_FIELD("MISSING_REQUIRED_FIELD", 400), // 400 Bad Request

    // HTTP 通用状态
    BAD_REQUEST("BAD_REQUEST", 400), // 400 Bad Request
    UNAUTHORIZED("UNAUTHORIZED", 401), // 401 Unauthorized
    FORBIDDEN("FORBIDDEN", 403), // 403 Forbidden
    NOT_FOUND("NOT_FOUND", 404), // 404 Not Found
    METHOD_NOT_ALLOWED("METHOD_NOT_ALLOWED", 405), // 405 Method Not Allowed
    CONFLICT("CONFLICT", 409), // 409 Conflict
    UNPROCESSABLE_ENTITY("UNPROCESSABLE_ENTITY", 422), // 422 Unprocessable Entity

    // 系统错误
    INTERNAL_SERVER_ERROR("INTERNAL_SERVER_ERROR", 500), // 500 Internal Server Error
    SYSTEM_ERROR("SYSTEM_ERROR", 500), // 500 Internal Server Error
    SERVICE_UNAVAILABLE("SERVICE_UNAVAILABLE", 503), // 503 Service Unavailable

    // 权限与令牌
    UNAUTHORIZED_ACCESS("UNAUTHORIZED_ACCESS", 401), // 401 Unauthorized
    ACCESS_DENIED("ACCESS_DENIED", 403), // 403 Forbidden
    TOKEN_EXPIRED("TOKEN_EXPIRED", 401), // 401 Unauthorized
    INVALID_TOKEN("INVALID_TOKEN", 401), // 401 Unauthorized

    // 业务操作失败
    OPERATION_FAILED("OPERATION_FAILED", 500), // 500 Internal Server Error
    DATA_INTEGRITY_VIOLATION("DATA_INTEGRITY_VIOLATION", 409), // 409 Conflict

    // 网络与超时
    NETWORK_ERROR("NETWORK_ERROR", 500), // 500 Internal Server Error
    TIMEOUT_ERROR("TIMEOUT_ERROR", 408); // 408 Request Timeout

    private final String code;
    private final int status;

    ErrorCode(String code, int status) {
        this.code = code;
        this.status = status;
    }

}