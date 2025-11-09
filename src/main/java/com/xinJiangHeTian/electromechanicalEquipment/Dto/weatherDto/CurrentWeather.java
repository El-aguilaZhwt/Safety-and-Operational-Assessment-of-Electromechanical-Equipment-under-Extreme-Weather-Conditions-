package com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto;

import lombok.Data;

@Data
public class CurrentWeather {
    private double temperature;
    private double humidity;
    private double windSpeed;
    private double pm25;
    private double pm10;
}
