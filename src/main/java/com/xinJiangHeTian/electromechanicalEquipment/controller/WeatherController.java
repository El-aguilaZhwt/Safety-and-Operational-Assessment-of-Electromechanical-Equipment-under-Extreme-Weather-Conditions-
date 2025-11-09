package com.xinJiangHeTian.electromechanicalEquipment.controller;

import com.xinJiangHeTian.electromechanicalEquipment.Dto.ApiResponse;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto.CountyRequest;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto.CurrentWeather;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto.WeatherResponse;
import com.xinJiangHeTian.electromechanicalEquipment.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author El aguila
 * @version 1.0
 * @description: 数字孪生大屏后台控制层
 * @date 2025/11/8 23:06
 */
@RestController
@RequestMapping("/weather")
@Validated
public class WeatherController {

    @Autowired
    private WeatherService weatherService;


    // 获取县区天气数据
    @PostMapping("/county-data")
    public ApiResponse<WeatherResponse> getCountyWeatherData(@Validated @RequestBody CountyRequest request) throws Exception {
        // 处理县区天气数据请求
        // 根据县区名称查询坐标信息
        Map<String, Double> coordinates = weatherService.getCoordinatesByCounty(request.getCounty());

        WeatherResponse response = new WeatherResponse();

        if (coordinates != null) {
            // 提取经纬度坐标
            Double lon = coordinates.get("lon");
            Double lat = coordinates.get("lat");

            // 根据坐标获取气象数据
            CurrentWeather weatherData = weatherService.getWeatherData(lon, lat);

            //将CurrentWeather注入WeatherResponse
            response.setCurrentWeather(weatherData);
        } else {
            // 坐标查询失败：返回错误信息
            return ApiResponse.error("无法找到该县的坐标信息", 401);
        }
        return ApiResponse.success("实时数据响应成功",response);
    }

    //县初始实时气象数据（用于最初打开屏幕）
    @PostMapping("/initial-data")
    public ApiResponse<WeatherResponse> getInitialCountyWeatherData() throws Exception {
        WeatherResponse response = new WeatherResponse();
        Map<String,Double> coordinates = weatherService.getInitialWeatherData();
        if (coordinates != null) {
            // 提取经纬度坐标
            Double lon = coordinates.get("lon");
            Double lat = coordinates.get("lat");

            // 根据坐标获取气象数据
            CurrentWeather weatherData = weatherService.getWeatherData(lon, lat);

            //将CurrentWeather注入WeatherResponse
            response.setCurrentWeather(weatherData);
        } else {
            // 坐标查询失败：返回错误信息
            return ApiResponse.error("页面实时气象数据初始化失败", 401);
        }
        return ApiResponse.success("实时数据响应成功",response);
    }
    // 根据经纬度获取预报数据
    @GetMapping("/forecast")
    public ApiResponse<WeatherResponse> getForecastData(
            @RequestParam double lon,
            @RequestParam double lat) throws Exception {
        return ApiResponse.success(weatherService.getForecastByCoordinate(lon, lat));
    }

    // 根据县名称获取预报数据
    @PostMapping("/forecast-county")
    public ApiResponse<WeatherResponse> getForecastDataByCounty(
            @Validated @RequestBody CountyRequest request) throws Exception {
        Map<String,Double> coordinates = weatherService.getInitialWeatherData();
        if (coordinates != null) {
            // 提取经纬度坐标
            Double lon = coordinates.get("lon");
            Double lat = coordinates.get("lat");
            return ApiResponse.success(weatherService.getForecastByCoordinate(lon, lat));
        }else{
            return ApiResponse.error("七日气象预报数据初始化失败", 401);
        }
    }
}
