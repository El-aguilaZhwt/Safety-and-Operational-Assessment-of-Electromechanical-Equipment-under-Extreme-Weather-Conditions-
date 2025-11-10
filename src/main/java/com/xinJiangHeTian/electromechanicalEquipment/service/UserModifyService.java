package com.xinJiangHeTian.electromechanicalEquipment.service;

import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import com.xinJiangHeTian.electromechanicalEquipment.mapper.UserMapper;
import com.xinJiangHeTian.electromechanicalEquipment.util.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author El aguila
 * @version 1.0
 * @description: 用户存储更新服务层
 * @date 2025/11/9 22:10
 */
@Service
@Slf4j
public class UserModifyService {
    @Autowired
    private UserMapper userMapper;


    public boolean currentPasswordConfirm(Long currentUserId, String currentPassword) {
        //在数据库中查找是否存在该用户，不存在直接返回
        User user = userMapper.findById(currentUserId);

        if (user == null) {
            log.error("the user is not existed");
            return false;
        }
        // 验证密码
        if (PasswordUtil.verifyPassword(currentPassword, user.getPassword())) {
            return true;
        }else{
            log.error("the password did not equals to that in database");
        }
        return false;
    }
    //根据id修改密码
    public boolean passwordModify(Long currentUserId, String newPassword) {
        String hashedNewPassword = PasswordUtil.encryptPassword(newPassword);
        return userMapper.updatePassword(currentUserId,hashedNewPassword)>0;
    }
    //根据id修改email
    public boolean emailModify(Long id, String newEmail) {
        return userMapper.updateEmail(id,newEmail)>0;
    }

    public boolean roleModify(Long userId, String newRole) {
        return userMapper.updateRole(userId,newRole)>0;
    }
}
