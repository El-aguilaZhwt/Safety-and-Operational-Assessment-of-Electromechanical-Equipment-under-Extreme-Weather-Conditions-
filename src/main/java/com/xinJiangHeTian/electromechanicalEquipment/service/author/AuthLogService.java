package com.xinJiangHeTian.electromechanicalEquipment.service.author;


import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.login.LoginResponseDto;
import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import com.xinJiangHeTian.electromechanicalEquipment.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author El aguila
 * @version 1.0
 * @description: 认证服务层
 * @date 2025/11/5 00:53
 */
@Slf4j
@Service
public class AuthLogService {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户登录认证
     */
    public LoginResponseDto authenticate(String username, String password, String role) {
        User user = userService.login(username, password);
        //如果没有该用户直接返回null
        if (user == null) {
            return null;
        }

        if(!user.getRole().equals(role)){
            log.info("用户身份比对失败");
            return null;
        }

        // 生成JWT令牌
        String token = jwtUtil.generateToken(user.getUsername(), user.getId(), user.getRole());
        Long expiresIn = jwtUtil.getRemainingTime(token);

        // 构建用户信息
        LoginResponseDto.UserInfo userInfo = new LoginResponseDto.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setEmail(user.getEmail());
        userInfo.setRole(user.getRole());
        userInfo.setAvatarUrl(user.getAvatarUrl());
        userInfo.setBio(user.getBio());

        return new LoginResponseDto(token, expiresIn, userInfo);
    }

    /**
     * 验证JWT令牌
     */
    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    /**
     * 从令牌中获取用户信息
     */
    public User getUserFromToken(String token) {
        if (!validateToken(token)) {
            return null;
        }

        Long userId = jwtUtil.getUserIdFromToken(token);
        return userService.findById(userId);
    }
}
