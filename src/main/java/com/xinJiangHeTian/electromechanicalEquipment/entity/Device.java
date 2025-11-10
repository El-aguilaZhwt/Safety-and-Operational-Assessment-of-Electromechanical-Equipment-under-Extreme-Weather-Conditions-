package com.xinJiangHeTian.electromechanicalEquipment.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author El aguila
 * @version 1.0
 * @description: 设备实体类
 * @date 2025/11/10 12:00
 */
@Data
public class Device {
    //设备id
    private Long id;
    //设备名称
    private String name;
    //设备承受最小温度
    private BigDecimal minTemperature;
    //设备承受最大温度
    private BigDecimal maxTemperature;
    //设备承受最小湿度
    private BigDecimal minHumidity;
    //设备承受最大湿度
    private BigDecimal maxHumidity;
    //设备承受最小风速
    private BigDecimal minWindSpeed;
    //设备承受最大风速
    private BigDecimal maxWindSpeed;
    //设备是否公开
    private Boolean isPublic;
    //设备添加人
    private Long createdBy;
    //设备创建时间
    private LocalDateTime createdTime;
    //设备更新时间
    private LocalDateTime updatedTime;
}