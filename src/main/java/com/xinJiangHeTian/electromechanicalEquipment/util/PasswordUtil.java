package com.xinJiangHeTian.electromechanicalEquipment.util;


import com.xinJiangHeTian.electromechanicalEquipment.constant.PasswordConstant;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * @author El aguila
 * @version 1.0
 * @description: 密码加密组件，采用JWT令牌发放/验证身份，采用HMAC_SHA256算法进行加密
 * @date 2025/11/5 00:21
 */
public class PasswordUtil {
    /**
     * 使用HMAC-SHA256加密密码
     */
    public static String encryptPassword(String password) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(PasswordConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8), PasswordConstant.HMAC_SHA256);
            Mac mac = Mac.getInstance(PasswordConstant.HMAC_SHA256);
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("密码加密失败", e);
        }
    }

    /**
     * 验证密码
     */
    public static boolean verifyPassword(String inputPassword, String storedHash) {
        String inputHash = encryptPassword(inputPassword);
        return inputHash.equals(storedHash);
    }

    /**
     * 生成随机盐
     */
    public static String generateSalt() {
        byte[] salt = new byte[16];
        new java.security.SecureRandom().nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 带盐的密码加密
     */
    public static String encryptPasswordWithSalt(String password, String salt) {
        return encryptPassword(password + salt);
    }
}
