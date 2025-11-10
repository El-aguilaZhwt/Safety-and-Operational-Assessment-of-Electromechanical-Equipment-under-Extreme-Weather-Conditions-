package com.xinJiangHeTian.electromechanicalEquipment.aHashedPassword;

import com.xinJiangHeTian.electromechanicalEquipment.constant.PasswordConstant;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class PasswordHash {
    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
        String password = "newpass123";
        SecretKeySpec secretKeySpec = new SecretKeySpec(PasswordConstant.SECRET_KEY.getBytes(StandardCharsets.UTF_8), PasswordConstant.HMAC_SHA256);
        Mac mac = Mac.getInstance(PasswordConstant.HMAC_SHA256);
        mac.init(secretKeySpec);
        byte[] hash = mac.doFinal(password.getBytes(StandardCharsets.UTF_8));
        System.out.println(Base64.getEncoder().encodeToString(hash));
    }
}
