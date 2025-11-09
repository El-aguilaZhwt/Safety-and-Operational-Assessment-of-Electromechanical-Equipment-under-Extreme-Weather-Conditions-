package com.xinJiangHeTian.electromechanicalEquipment.Dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author El aguila
 * @version 1.0
 * @description: 统一响应dto
 * @date 2025/11/5 00:45
 */
@Data
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private T data;
    private String timestamp;
    private int code;

    public ApiResponse(boolean success, String message, T data, int code) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.code = code;
        this.timestamp = LocalDateTime.now().toString();
    }

    // 成功响应
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(true, "成功", data, 200);
    }

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(true, message, data, 200);
    }

    // 失败响应
    public static <T> ApiResponse<T> error(String message) {
        return new ApiResponse<>(false, message, null, 400);
    }

    public static <T> ApiResponse<T> error(String message, int code) {
        return new ApiResponse<>(false, message, null, code);
    }
}
