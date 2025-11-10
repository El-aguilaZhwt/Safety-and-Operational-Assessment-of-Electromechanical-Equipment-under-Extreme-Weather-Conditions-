package com.xinJiangHeTian.electromechanicalEquipment.Dto.UserModifyDto;

import lombok.Data;

@Data
public class RoleModifyRequest {
    private Long userId;
    private String password;
    private String newRole;
}
