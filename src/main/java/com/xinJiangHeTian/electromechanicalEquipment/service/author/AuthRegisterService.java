package com.xinJiangHeTian.electromechanicalEquipment.service.author;


import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.login.LoginResponseDto;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.register.RegisterRequestDto;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.register.RegisterResponseDto;
import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import com.xinJiangHeTian.electromechanicalEquipment.mapper.UserMapper;
import com.xinJiangHeTian.electromechanicalEquipment.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

/**
 * @author El aguila
 * @version 1.0
 * @description: 注册服务层
 * @date 2025/11/5 12:45
 */
@Service
public class AuthRegisterService {
    @Autowired
    private UserMapper userMapper;
    // 用户名正则：字母数字下划线，3-20位
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{3,20}$");
    // 密码强度：至少包含字母和数字
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d).{6,20}$");

    /**
     * 用户注册
     */
    public RegisterResponseDto register(RegisterRequestDto request) {
        // 1. 数据验证
        String validationError = validateRegisterRequest(request);
        if (validationError != null) {
            return new RegisterResponseDto(false, validationError, null);
        }

        // 2. 检查用户名和邮箱是否已存在
        if (userMapper.findByUsername(request.getUsername()) != null) {
            return new RegisterResponseDto(false, "用户名已存在", null);
        }

        if (userMapper.findByEmail(request.getEmail()) != null) {
            return new RegisterResponseDto(false, "邮箱已被注册", null);
        }

        // 3. 创建用户实体
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(PasswordUtil.encryptPassword(request.getPassword()));
        user.setRole("USER"); // 默认用户角色
        user.setBio(request.getBio());
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // 4. 保存用户
        int result = userMapper.insert(user);
        if (result > 0) {
            // 构建响应数据
            RegisterResponseDto.UserInfo userInfo = new RegisterResponseDto.UserInfo();
            userInfo.setId(user.getId());
            userInfo.setUsername(user.getUsername());
            userInfo.setEmail(user.getEmail());
            userInfo.setRole(user.getRole());
            userInfo.setBio(user.getBio());
            return new RegisterResponseDto(true, "注册成功", userInfo);
        } else {
            return new RegisterResponseDto(false, "注册失败，请稍后重试", null);
        }
    }
    /**
     * 验证注册请求数据
     */
    private String validateRegisterRequest(RegisterRequestDto request) {
        // 用户名格式验证
        if (!USERNAME_PATTERN.matcher(request.getUsername()).matches()) {
            return "用户名只能包含字母、数字和下划线，长度3-20位";
        }

        // 密码强度验证
        if (!PASSWORD_PATTERN.matcher(request.getPassword()).matches()) {
            return "密码必须包含字母和数字，长度6-20位";
        }

        // 确认密码验证
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            return "两次输入的密码不一致";
        }

        // 邮箱格式验证（Spring的@Email已经验证，这里做额外检查）
        if (!isValidEmail(request.getEmail())) {
            return "邮箱格式不正确";
        }

        return null;
    }

    /**
     * 邮箱格式验证
     */
    private boolean isValidEmail(String email) {
        if (!StringUtils.hasText(email)) {
            return false;
        }
        return email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
    }

    /**
     * 注册后自动登录
     */
    @Autowired
    private AuthLogService authLogService;
    public LoginResponseDto registerAndLogin(RegisterRequestDto request) {
        RegisterResponseDto registerResponse = register(request);
        if (!registerResponse.isSuccess()) {
            return null;
        }
        // 注册成功后自动登录
        return authLogService.authenticate(request.getUsername(), request.getPassword(), "USER");
    }

}
