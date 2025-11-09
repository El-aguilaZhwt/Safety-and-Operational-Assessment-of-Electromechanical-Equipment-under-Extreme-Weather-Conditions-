package com.xinJiangHeTian.electromechanicalEquipment.constant;

import org.springframework.stereotype.Component;

/**
 * @author El aguila
 * @version 1.0
 * @description: 密码安全模块的常理配置
 * @date 2025/11/5 01:09
 */
@Component
public class PasswordConstant {
    //    加密算法
    public static final String HMAC_SHA256 = "HmacSHA256";
    //  密钥
    public static final String SECRET_KEY = "photography-community-2025-secret-key-with-32-chars"; // 应该从配置读取
}
