package com.xinJiangHeTian.electromechanicalEquipment.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author El aguila
 * @version 1.0
 * @description: 用户实体类
 * @date 2025/11/4 23:57
 */
@Data
public class User {
//    用户id
    private Long id;
//    用户昵称
    private String username;
//    用户邮箱
    private String email;
//    用户密码
    private String password;
//    用户身份
    private String role; // GUEST, USER, ADMIN
//    用户头像内容
    private String avatarUrl;       //用户头像URL

    private String avatarThumbUrl;    // 缩略图URL

    private String avatarOriginalName; // 原文件名

    private Long avatarFileSize;       // 文件大小

    private LocalDateTime avatarUpdatedAt; // 头像更新时间
//    用户简介
    private String bio;
//    创建时间
    private LocalDateTime createdAt;
//    更新时间
    private LocalDateTime updatedAt;

}
