package com.xinJiangHeTian.electromechanicalEquipment.service.author;


import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import com.xinJiangHeTian.electromechanicalEquipment.mapper.UserMapper;
import com.xinJiangHeTian.electromechanicalEquipment.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
/**
 * @author El aguila
 * @version 1.0
 * @description: 用户服务层
 * @date 2025/11/5 00:53
 */
@Slf4j
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登录
     */
    public User login(String username, String password) {
        //在数据库中查找是否存在该用户，不存在直接返回
        User user = userMapper.findByUsername(username);

        if (user == null) {
            log.info("用户不存在");
            return null;
        }
        // 验证密码
        if (PasswordUtil.verifyPassword(password, user.getPassword())) {
            return user;
        }
        return null;
    }

    /**
     * 用户注册
     */
    public boolean register(User user) {
        // 检查用户名是否已存在
        if (userMapper.findByUsername(user.getUsername()) != null) {
            return false;
        }

        // 加密密码
        user.setPassword(PasswordUtil.encryptPassword(user.getPassword()));
        user.setRole("USER"); // 默认用户角色

        return userMapper.insert(user) > 0;
    }

    /**
     * 根据ID查找用户
     */
    public User findById(Long id) {
        return userMapper.findById(id);
    }

    /**
     * 查找所有用户
     */
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 更新用户信息
     */
    public boolean updateUser(User user) {
        return userMapper.update(user) > 0;
    }

    /**
     * 检查用户名是否存在
     */
    public boolean isUsernameExists(String username) {
        return userMapper.findByUsername(username) != null;
    }

    /**
     * 检查邮箱是否存在
     */
    public boolean isEmailExists(String email) {
        return userMapper.findByEmail(email) != null;
    }
}
