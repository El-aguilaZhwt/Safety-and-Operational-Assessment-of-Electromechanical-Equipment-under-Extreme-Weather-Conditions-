package com.xinJiangHeTian.electromechanicalEquipment.Dto.UserModifyDto;

import lombok.Data;

@Data
public class EmailModifyRequest {
    private Long userId;
    private String password;
    private String newEmail;
}
