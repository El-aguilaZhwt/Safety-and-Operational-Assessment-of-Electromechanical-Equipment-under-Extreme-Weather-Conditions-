package com.xinJiangHeTian.electromechanicalEquipment.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author El aguila
 * @version 1.0
 * @description: 设备用户关联表
 * @date 2025/11/10 12:01
 */
@Data
public class DeviceUser {
    //关联id
    private Long id;
    //设备id
    private Long deviceId;
    //用户id
    private Long userId;
    //是否为所有者
    private Boolean isOwner;
    //关联信息添加时间
    private LocalDateTime addedTime;
}