package com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author El aguila
 * @version 1.0
 * @description: 设备创建需要信息
 * @date 2025/11/10 13:06
 */
@Data
public class CreateDeviceRequest {
    //前端直接传入userId
    private Long userId;

    @NotBlank(message = "设备名称不能为空")
    @Size(max = 100, message = "设备名称不能超过100个字符")
    private String name;

    @NotNull(message = "最低温度不能为空")
    private BigDecimal minTemperature;

    @NotNull(message = "最高温度不能为空")
    private BigDecimal maxTemperature;

    @NotNull(message = "最低湿度不能为空")
    private BigDecimal minHumidity;

    @NotNull(message = "最高湿度不能为空")
    private BigDecimal maxHumidity;

    @NotNull(message = "最低风速不能为空")
    private BigDecimal minWindSpeed;

    @NotNull(message = "最高风速不能为空")
    private BigDecimal maxWindSpeed;

    private Boolean isPublic = false;
}

