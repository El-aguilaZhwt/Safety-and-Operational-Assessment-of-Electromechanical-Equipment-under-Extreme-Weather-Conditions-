package com.xinJiangHeTian.electromechanicalEquipment.Dto;

import lombok.Data;

/**
 * 用户登录请求数据传输对象（DTO）
 * 用于接收前端传递的用户登录认证信息
 */
@Data
public class LoginRequest {
    /**
     * 用户名
     * 必填字段，用于标识用户身份
     */
    private String username;

    /**
     * 密码
     * 必填字段，用于身份验证
     * 建议前端传输时进行加密处理
     */
    private String password;

    /**
     * 用户身份标识
     * 用于区分不同用户角色和权限
     * 示例：admin-管理员, user-普通用户, visitor-访客
     */
    private String identity;

    // Getters and Setters (由Lombok @Data注解自动生成)
}
