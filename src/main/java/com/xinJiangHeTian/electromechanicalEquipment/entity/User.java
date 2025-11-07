package com.xinJiangHeTian.electromechanicalEquipment.entity;

import lombok.Data;

import java.time.LocalDateTime;


/**
 * 用户实体类
 * 用于管理系统用户信息，支持不同身份角色的权限管理
 */
@Data
public class User {
    /**
     * 用户主键ID
     * 唯一标识每个用户记录，自动递增
     */
    private Long id;

    /**
     * 用户名
     * 用户登录标识，要求唯一且不为空
     * 格式：4-20位字母、数字或下划线组合
     */
    private String username;

    /**
     * 用户密码
     * 存储加密后的密码哈希值，不存储明文密码
     * 建议使用BCrypt等安全加密算法
     */
    private String password;

    /**
     * 用户身份角色
     * 用于权限控制和功能访问控制
     * 默认值："visitor"（访客）
     * 可选值：admin-管理员, user-普通用户, operator-操作员, visitor-访客
     */
    private String identity = "visitor";

    /**
     * 用户账号创建时间
     * 记录用户注册的系统时间
     * 格式：ISO 8601标准格式（yyyy-MM-dd'T'HH:mm:ss）
     */
    private LocalDateTime createdAt;

    /**
     * 实体创建时的回调方法
     * 在创建新用户时自动设置创建时间为当前系统时间
     * 通常由JPA的@PrePersist注解触发或在业务逻辑中手动调用
     */
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters (由Lombok @Data注解自动生成)
}
