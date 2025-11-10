package com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author El aguila
 * @version 1.0
 * @description: 设备Dto
 * @date 2025/11/10 13:06
 */
@Data
public class DeviceDto {
    private Long id;
    private String name;
    private BigDecimal minTemperature;
    private BigDecimal maxTemperature;
    private BigDecimal minHumidity;
    private BigDecimal maxHumidity;
    private BigDecimal minWindSpeed;
    private BigDecimal maxWindSpeed;
    private Boolean isPublic;
    private Boolean isOwner;
    private LocalDateTime createdTime;
}