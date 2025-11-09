package com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDtoII;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 七日天气预报数据传输对象（DTO）
 * 用于封装和传输未来七天的天气预报数据
 */
@Data
public class SevenDayWeatherDTO {
    /**
     * 日期
     * 格式：YYYY-MM-DD
     */
    private String date;

    /**
     * 最低温度
     * 单位：摄氏度（℃）
     */
    @JsonProperty("temperature_min")
    private Double temperatureMin;

    /**
     * 最高温度
     * 单位：摄氏度（℃）
     */
    @JsonProperty("temperature_max")
    private Double temperatureMax;

    /**
     * 湿度
     * 单位：百分比（%），范围：0-100
     */
    private Double humidity;

    /**
     * 风速
     * 单位：米/秒（m/s）
     */
    @JsonProperty("wind_speed")
    private Double windSpeed;

    /**
     * 风向
     * 例如：东北风、西南风等
     */
    @JsonProperty("wind_direction")
    private String windDirection;
}