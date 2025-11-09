package com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDtoII;

import lombok.Data;

/**
 * 实时天气数据数据传输对象（DTO）
 * 用于封装和传输当前实时天气监测数据
 */
@Data
public class WeatherDataDTO {
    /**
     * 温度
     * 单位：摄氏度（℃）
     * 精度：保留一位小数
     */
    private Double temperature;

    /**
     * 湿度
     * 单位：百分比（%）
     * 范围：0-100，表示相对湿度
     */
    private Double humidity;

    /**
     * 风速
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

    // Getters and Setters (由Lombok @Data注解自动生成)
}
