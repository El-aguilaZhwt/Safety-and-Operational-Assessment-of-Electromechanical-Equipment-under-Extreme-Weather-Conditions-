package com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author El aguila
 * @version 1.0
 * @description: 处理用户和设备关系的请求
 * @date 2025/11/10 13:14
 */
@Data
public class UserDeviceManageRequest {
    @NotNull(message = "设备ID不能为空")
    private Long deviceId;

    @NotNull(message = "用户ID不能为空")
    private Long userId;
}
