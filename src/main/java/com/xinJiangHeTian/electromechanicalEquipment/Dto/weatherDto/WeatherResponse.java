package com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto;

import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {
    private CurrentWeather currentWeather;
    private List<ForecastData> forecastData;
}
