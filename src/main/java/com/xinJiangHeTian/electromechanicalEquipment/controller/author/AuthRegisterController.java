package com.xinJiangHeTian.electromechanicalEquipment.controller.author;

import com.xinJiangHeTian.electromechanicalEquipment.Dto.ApiResponse;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.author.login.LoginResponseDto;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.author.register.RegisterRequestDto;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.author.register.RegisterResponseDto;
import com.xinJiangHeTian.electromechanicalEquipment.service.author.AuthRegisterService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author El aguila
 * @version 1.0
 * @description: 用户注册控制层
 * @date 2025/11/5 12:38
 */
@RestController
@RequestMapping("/auth")
@Validated
public class AuthRegisterController {

    @Autowired
    private AuthRegisterService authRegisterService;

    @PostMapping("/register")
    public ApiResponse<RegisterResponseDto> register(@Validated @RequestBody RegisterRequestDto registerRequest){
        RegisterResponseDto response = authRegisterService.register(registerRequest);
        if (response.isSuccess()) {
            return ApiResponse.success(response.getMessage(), response);
        } else {
            return ApiResponse.error(response.getMessage(), 400);
        }
    }

    /**
     * 用户注册并自动登录
     */
    @PostMapping("/register-login")
    public ApiResponse<LoginResponseDto> registerAndLogin(@Valid @RequestBody RegisterRequestDto registerRequest) {
        LoginResponseDto response = authRegisterService.registerAndLogin(registerRequest);
        if (response != null) {
            return ApiResponse.success("注册并登录成功", response);
        } else {
            return ApiResponse.error("注册失败", 400);
        }
    }

//    /**
//     * 检查用户名是否可用
//     */
//    @GetMapping("/check-username")
//    public ApiResponse<Boolean> checkUsername(@RequestParam String username) {
//        boolean available = !authRegisterService.getUserService().isUsernameExists(username);
//        return ApiResponse.success(available ? "用户名可用" : "用户名已存在", available);
//    }
//
//    /**
//     * 检查邮箱是否可用
//     */
//    @GetMapping("/check-email")
//    public ApiResponse<Boolean> checkEmail(@RequestParam String email) {
//        boolean available = !authRegisterService.getUserService().isEmailExists(email);
//        return ApiResponse.success(available ? "邮箱可用" : "邮箱已被注册", available);
//    }
}
