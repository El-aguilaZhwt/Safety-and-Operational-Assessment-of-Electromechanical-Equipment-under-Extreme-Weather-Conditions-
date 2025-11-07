package com.xinJiangHeTian.electromechanicalEquipment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeatherService {
    @Value("${weather.api.key}")
    private String apiKey;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Map<String, Map<String, Double>> countyCoordinates = Map.of(
            "和田市", Map.of("lon", 79.9167, "lat", 37.1167),
            "和田县", Map.of("lon", 79.9083, "lat", 37.1083),
            "墨玉县", Map.of("lon", 79.7500, "lat", 37.2833),
            "皮山县", Map.of("lon", 78.2833, "lat", 37.6167),
            "洛浦县", Map.of("lon", 80.1833, "lat", 37.0833),
            "策勒县", Map.of("lon", 80.4167, "lat", 37.0000),
            "于田县", Map.of("lon", 81.6667, "lat", 36.8667),
            "民丰县", Map.of("lon", 82.6833, "lat", 37.0500),
            "且末县", Map.of("lon", 85.5167, "lat", 38.1167)
    );

    public Map<String, Object> getWeatherData(Double lon, Double lat) throws Exception {
        String weatherUrl = String.format(
                "https://devapi.qweather.com/v7/weather/now?key=%s&location=%s,%s",
                apiKey, lon, lat
        );
        String airUrl = String.format(
                "https://devapi.qweather.com/v7/air/now?key=%s&location=%s,%s",
                apiKey, lon, lat
        );

        Map<String, Object> weatherData = new HashMap<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 获取天气数据
            HttpGet weatherRequest = new HttpGet(weatherUrl);
            String weatherResponse = EntityUtils.toString(client.execute(weatherRequest).getEntity());
            JsonNode weatherJson = objectMapper.readTree(weatherResponse);

            // 获取空气质量数据
            HttpGet airRequest = new HttpGet(airUrl);
            String airResponse = EntityUtils.toString(client.execute(airRequest).getEntity());
            JsonNode airJson = objectMapper.readTree(airResponse);

            weatherData.put("temperature", weatherJson.path("now").path("temp").asDouble());
            weatherData.put("humidity", weatherJson.path("now").path("humidity").asDouble());
            weatherData.put("wind_speed", weatherJson.path("now").path("windSpeed").asDouble());
            weatherData.put("pm25", airJson.path("now").path("pm2p5").asDouble());
            weatherData.put("pm10", airJson.path("now").path("pm10").asDouble());

        } catch (Exception e) {
            weatherData.put("temperature", "N/A");
            weatherData.put("humidity", "N/A");
            weatherData.put("wind_speed", "N/A");
            weatherData.put("pm25", "N/A");
            weatherData.put("pm10", "N/A");
        }

        return weatherData;
    }

    public List<Map<String, Object>> getSevenDayWeather(Double lon, Double lat) throws Exception {
        String weatherUrl = String.format(
                "https://devapi.qweather.com/v7/weather/7d?key=%s&location=%s,%s",
                apiKey, lon, lat
        );

        List<Map<String, Object>> weatherDataList = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(weatherUrl);
            String response = EntityUtils.toString(client.execute(request).getEntity());
            JsonNode json = objectMapper.readTree(response);

            JsonNode daily = json.path("daily");
            for (JsonNode day : daily) {
                Map<String, Object> dailyData = new HashMap<>();
                dailyData.put("date", day.path("fxDate").asText());
                dailyData.put("temperature_min", day.path("tempMin").asDouble());
                dailyData.put("temperature_max", day.path("tempMax").asDouble());
                dailyData.put("humidity", day.path("humidity").asDouble());
                dailyData.put("wind_speed", day.path("windSpeedDay").asDouble());
                dailyData.put("wind_direction", day.path("wind360Day").asText());
                weatherDataList.add(dailyData);
            }
        } catch (Exception e) {
            // 处理异常
        }

        return weatherDataList;
    }

    public Map<String, Double> getCoordinatesByCounty(String county) {
        return countyCoordinates.get(county);
    }
}
