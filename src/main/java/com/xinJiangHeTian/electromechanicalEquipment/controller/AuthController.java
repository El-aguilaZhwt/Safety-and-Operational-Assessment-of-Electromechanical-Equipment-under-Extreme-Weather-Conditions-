package com.xinJiangHeTian.electromechanicalEquipment.controller;

import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import com.xinJiangHeTian.electromechanicalEquipment.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author El aguila
 * @version 1.0
 * @description: 用户认证控制器
 *  * 处理用户登录、注册、注销等认证相关请求
 * @date 2025/11/7 23:59
 */
@RestController
@RequestMapping("/api")
public class AuthController {

    private final UserService userService;

    /**
     * 构造函数，依赖注入UserService
     * @param userService 用户服务类实例
     */
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 用户登录接口
     * @param loginData 登录数据，包含用户名、密码和身份信息
     * @param session HTTP会话对象，用于存储用户登录状态
     * @return 登录结果响应实体，包含成功状态和跳转路径或错误信息
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginData, HttpSession session) {
        // 从请求体中提取登录参数
        String username = loginData.get("username");
        String password = loginData.get("password");
        String identity = loginData.get("identity");

        // 调用服务层进行用户认证
        User user = userService.authenticate(username, password, identity);

        if (user != null) {
            // 认证成功：在会话中存储用户信息
            session.setAttribute("username", username);
            session.setAttribute("identity", identity);

            // 构建成功响应
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("redirect", "/home"); // 登录成功后跳转到首页
            return ResponseEntity.ok(response);
        } else {
            // 认证失败：返回错误信息
            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "用户名、密码或身份错误");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 用户注册接口
     * @param registerData 注册数据，包含用户名、密码和确认密码
     * @return 注册结果响应实体，包含成功状态和相应消息
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> registerData) {
        // 从请求体中提取注册参数
        String username = registerData.get("newUsername");
        String password = registerData.get("newPassword");
        String confirmPassword = registerData.get("confirmPassword");

        // 调用服务层进行用户注册
        boolean success = userService.register(username, password, confirmPassword);

        // 构建响应对象
        Map<String, Object> response = new HashMap<>();
        if (success) {
            // 注册成功
            response.put("success", true);
            response.put("message", "注册成功，请登录");
            return ResponseEntity.ok(response);
        } else {
            // 注册失败
            response.put("success", false);
            response.put("message", "注册失败，用户名已存在或密码不匹配");
            return ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * 用户注销接口
     * @param session HTTP会话对象，用于清除用户登录状态
     * @return 注销成功响应（无内容）
     */
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        // 使当前会话失效，清除所有会话属性
        session.invalidate();
        // 返回200状态码表示注销成功
        return ResponseEntity.ok().build();
    }
}
