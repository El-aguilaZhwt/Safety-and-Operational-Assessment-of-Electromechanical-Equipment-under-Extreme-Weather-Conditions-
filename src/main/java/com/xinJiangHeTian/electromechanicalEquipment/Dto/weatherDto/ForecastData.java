package com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto;

import lombok.Data;

@Data
public class ForecastData {
    private String date;
    private double temperatureMin;
    private double temperatureMax;
    private double humidity;
    private double windSpeed;
    private int windDirection;
}
