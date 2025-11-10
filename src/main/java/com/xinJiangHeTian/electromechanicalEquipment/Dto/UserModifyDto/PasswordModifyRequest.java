package com.xinJiangHeTian.electromechanicalEquipment.Dto.UserModifyDto;

import lombok.Data;

/**
 * @author El aguila
 * @version 1.0
 * @description: 修改密码请求dto,当前密码和新密码
 * @date 2025/11/9 21:56
 */
@Data
public class PasswordModifyRequest {
    private Long currentUserId;
    private String currentPassword;
    private String newPassword;
}
