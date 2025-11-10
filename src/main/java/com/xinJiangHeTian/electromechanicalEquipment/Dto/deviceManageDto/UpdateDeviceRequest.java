package com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateDeviceRequest {
    //直接传userId
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

    private Boolean isPublic;
}