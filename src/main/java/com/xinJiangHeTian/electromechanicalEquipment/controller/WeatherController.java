package com.xinJiangHeTian.electromechanicalEquipment.controller;

import com.xinJiangHeTian.electromechanicalEquipment.service.WeatherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author El aguila
 * @version 1.0
 * @description:  * 气象数据控制器
 *  * 处理气象数据查询、坐标保存等相关请求
 * @date 2025/11/8 00:00
 */
@RestController
@RequestMapping("/api")
public class WeatherController {

    private final WeatherService weatherService;

    /**
     * 构造函数，依赖注入WeatherService
     * @param weatherService 气象服务类实例
     */
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    /**
     * 首页数据接口
     * 获取用户身份信息和默认位置的气象数据
     * @param session HTTP会话对象，用于获取用户登录状态
     * @return 包含用户身份、用户名和气象数据的响应实体
     */
    @GetMapping("/home")
    public ResponseEntity<?> home(HttpSession session) {
        // 从会话中获取用户身份和用户名
        String identity = (String) session.getAttribute("identity");
        String username = (String) session.getAttribute("username");

        // 如果身份为空，默认为访客身份
        if (identity == null) identity = "visitor";

        // 构建响应对象
        Map<String, Object> response = new HashMap<>();
        response.put("identity", identity);
        response.put("username", username);

        try {
            // 设置默认坐标（示例坐标）
            Double lon = 81.27;
            Double lat = 36.69;

            // 调用服务层获取气象数据
            Map<String, Object> weatherData = weatherService.getWeatherData(lon, lat);
            response.put("weather_data", weatherData);

        } catch (Exception e) {
            // 气象数据获取失败时返回空对象，保证接口不报错
            response.put("weather_data", new HashMap<>());
        }

        return ResponseEntity.ok(response);
    }

    /**
     * 保存坐标并获取气象数据接口
     * 根据县区名称查询坐标并获取对应气象数据
     * @param data 请求数据，包含县区名称
     * @return 包含操作状态、县区信息和气象数据的响应实体
     */
    @PostMapping("/save-coordinates")
    public ResponseEntity<?> saveCoordinates(@RequestBody Map<String, String> data) {
        // 从请求体中提取县区名称
        String county = data.get("county");

        try {
            // 根据县区名称查询坐标信息
            Map<String, Double> coordinates = weatherService.getCoordinatesByCounty(county);

            if (coordinates != null) {
                // 提取经纬度坐标
                Double lon = coordinates.get("lon");
                Double lat = coordinates.get("lat");

                // 根据坐标获取气象数据
                Map<String, Object> weatherData = weatherService.getWeatherData(lon, lat);

                // 构建成功响应
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("county", county);
                response.put("weather", weatherData);

                return ResponseEntity.ok(response);
            } else {
                // 坐标查询失败：返回错误信息
                Map<String, Object> response = new HashMap<>();
                response.put("status", "error");
                response.put("message", "无法找到该县的坐标信息");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            // 服务器内部错误处理
            Map<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "数据保存失败");
            return ResponseEntity.internalServerError().body(response);
        }
    }
}