package com.xinJiangHeTian.electromechanicalEquipment.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author El aguila
 * @version 1.0
 * @description: 传感器数据实体类
 *  * 对应数据库表：sensor_data
 * @date 2025/11/8 00:03
 */
@Data
public class SensorData {
    /**
     * 主键ID
     * 唯一标识每条传感器数据记录
     */
    private Long id;

    /**
     * 温度值
     * 单位：摄氏度（℃）
     * 精度：保留一位小数
     */
    private Double temperature;

    /**
     * 湿度值
     * 单位：百分比（%）
     * 范围：0-100，表示相对湿度
     */
    private Double humidity;

    /**
     * 风速值
     * 单位：米/秒（m/s）
     * 表示当前平均风速
     */
    private Double windSpeed;

    /**
     * PM2.5浓度
     * 单位：微克/立方米（μg/m³）
     * 空气质量重要指标，数值越高污染越严重
     */
    private Double pm25;

    /**
     * PM10浓度
     * 单位：微克/立方米（μg/m³）
     * 可吸入颗粒物浓度，数值越高污染越严重
     */
    private Double pm10;

    /**
     * 数据采集时间戳
     * 记录传感器数据采集的具体时间
     * 格式：ISO 8601标准格式（yyyy-MM-dd'T'HH:mm:ss）
     */
    private LocalDateTime timestamp;
}
