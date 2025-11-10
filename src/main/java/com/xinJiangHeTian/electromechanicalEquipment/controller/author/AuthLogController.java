package com.xinJiangHeTian.electromechanicalEquipment.controller.author;

import com.xinJiangHeTian.electromechanicalEquipment.Dto.ApiResponse;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.login.LoginRequestDto;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.login.LoginResponseDto;
import com.xinJiangHeTian.electromechanicalEquipment.service.author.AuthLogService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author El aguila
 * @version 1.0
 * @description: 用户认证控制层
 * @date 2025/11/5 00:57
 */

@RestController
@RequestMapping("/auth")
@Validated
public class AuthLogController {
    @Autowired
    private AuthLogService authLogService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public ApiResponse<LoginResponseDto> login(@Valid @RequestBody LoginRequestDto loginRequest) {
        LoginResponseDto response = authLogService.authenticate(
                loginRequest.getUsername(),
                loginRequest.getPassword(),
                loginRequest.getRole()
        );

        if (response == null) {
            return ApiResponse.error("用户名或密码错误", 401);
        }

        return ApiResponse.success("登录成功", response);
    }


    /**
     * 验证令牌有效性
     */
    @GetMapping("/validate")
    public ApiResponse<Boolean> validateToken(@RequestHeader("Authorization") String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ApiResponse.error("令牌格式错误", 401);
        }

        String token = authHeader.substring(7);
        boolean isValid = authLogService.validateToken(token);

        return ApiResponse.success(isValid ? "令牌有效" : "令牌无效", isValid);
    }

    /**
     * 刷新令牌（可选功能）
     */
    @PostMapping("/refresh")
    public ApiResponse<LoginResponseDto> refreshToken(@RequestHeader("Authorization") String authHeader) {
        // 实现令牌刷新逻辑
        return ApiResponse.error("功能开发中", 501);
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public ApiResponse<String> logout(@RequestHeader("Authorization") String authHeader) {
        // 在实际应用中，可以将令牌加入黑名单
        // 这里简单返回成功
        return ApiResponse.success("登出成功", null);
    }

}
