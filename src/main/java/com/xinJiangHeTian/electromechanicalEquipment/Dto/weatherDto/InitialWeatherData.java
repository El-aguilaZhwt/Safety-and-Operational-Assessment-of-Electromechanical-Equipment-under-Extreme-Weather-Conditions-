package com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto;

import lombok.Data;

@Data
public class InitialWeatherData {
    private double temperature;
    private double humidity;
    private double windSpeed;
    private double pm25;
    private double pm10;
    private double defaultLon;
    private double defaultLat;
}
