package com.xinJiangHeTian.electromechanicalEquipment.service;

import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import com.xinJiangHeTian.electromechanicalEquipment.mapper.UserMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public User authenticate(String username, String password, String identity) {
        User user = userMapper.findByUsername(username);
        if (user != null && passwordEncoder.matches(password, user.getPassword())
                && user.getIdentity().equals(identity)) {
            return user;
        }
        return null;
    }

    public boolean register(String username, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            return false;
        }

        if (userMapper.existsByUsername(username) > 0) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setIdentity("visitor");

        userMapper.insert(user);
        return true;
    }

    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }
}
