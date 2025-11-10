package com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.login;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author El aguila
 * @version 1.0
 * @description: 登录请求提交类
 * @date 2025/11/5 00:39
 */
@Data
public class LoginRequestDto {
    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "身份不能为空")
    private String role;
}
