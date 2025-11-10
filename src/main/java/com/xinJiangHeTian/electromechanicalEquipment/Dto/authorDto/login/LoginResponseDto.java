package com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.login;

import lombok.Data;

/**
 * @author El aguila
 * @version 1.0
 * @description: 登录响应dto
 * @date 2025/11/5 00:44
 */
@Data
public class LoginResponseDto {
    private String token;
    private String tokenType = "Bearer";
    private Long expiresIn;
    private UserInfo user;

    @Data
    public static class UserInfo {
        private Long id;
        private String username;
        private String email;
        private String role;
        private String avatarUrl;
        private String bio;
    }

    public LoginResponseDto(String token, Long expiresIn, UserInfo user) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.user = user;
    }
}
