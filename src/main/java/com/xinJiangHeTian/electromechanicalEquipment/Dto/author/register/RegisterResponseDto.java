package com.xinJiangHeTian.electromechanicalEquipment.Dto.author.register;

import lombok.Data;

@Data
public class RegisterResponseDto {
    private boolean success;
    private String message;
    private UserInfo user;

    @Data
    public static class UserInfo {
        private Long id;
        private String username;
        private String email;
        private String role;
        private String bio;
    }

    public RegisterResponseDto(boolean success, String message, UserInfo user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }
}
