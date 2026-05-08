package org.example.tieba.constants;

public class ErrorCodeConstants {

    public static final String SUCCESS = "SUCCESS";

    // 资源相关错误
    public static final String RESOURCE_ALREADY_EXISTS = "RESOURCE_ALREADY_EXISTS";  // 资源已存在
    public static final String RESOURCE_NOT_FOUND = "RESOURCE_NOT_FOUND";            // 资源未找到

    // 用户相关错误
    public static final String USER_NOT_FOUND = "USER_NOT_FOUND";                    // 用户未找到
    public static final String INVALID_USER_CREDENTIALS = "INVALID_USER_CREDENTIALS"; // 用户凭证无效

    // 参数相关错误
    public static final String INVALID_PARAMETER = "INVALID_PARAMETER";              // 参数无效
    public static final String MISSING_REQUIRED_FIELD = "MISSING_REQUIRED_FIELD";    // 缺少必填字段

    // 系统级别错误
    public static final String INTERNAL_SERVER_ERROR = "INTERNAL_SERVER_ERROR";      // 内部服务器错误
    public static final String UNAUTHORIZED_ACCESS = "UNAUTHORIZED_ACCESS";          // 未授权的访问
    public static final String FORBIDDEN = "FORBIDDEN";                              // 禁止访问
    public static final String BAD_REQUEST = "BAD_REQUEST";                          // 请求格式错误
    public static final String NOT_FOUND = "NOT_FOUND";                              // 请求资源未找到
    public static final String METHOD_NOT_ALLOWED = "METHOD_NOT_ALLOWED";            // HTTP 方法不被允许
    public static final String CONFLICT = "CONFLICT";                                // 请求存在冲突
    public static final String UNPROCESSABLE_ENTITY = "UNPROCESSABLE_ENTITY";        // 处理失败，实体无效

    // 通用错误码（适用于多个场景）
    public static final String SYSTEM_ERROR = "SYSTEM_ERROR";                        // 系统错误
    public static final String OPERATION_FAILED = "OPERATION_FAILED";                // 操作失败
    public static final String DATA_INTEGRITY_VIOLATION = "DATA_INTEGRITY_VIOLATION"; // 数据完整性违背
    public static final String SERVICE_UNAVAILABLE = "SERVICE_UNAVAILABLE";          // 服务不可用

    // 用户认证和授权错误
    public static final String UNAUTHORIZED = "UNAUTHORIZED";                        // 未授权
    public static final String TOKEN_EXPIRED = "TOKEN_EXPIRED";                      // token 过期
    public static final String INVALID_TOKEN = "INVALID_TOKEN";                      // token 无效
    public static final String ACCESS_DENIED = "ACCESS_DENIED";                      // 访问被拒绝

    // 网络错误
    public static final String NETWORK_ERROR = "NETWORK_ERROR";                      // 网络错误

    // 其他错误
    public static final String TIMEOUT_ERROR = "TIMEOUT_ERROR";                      // 超时错误
}