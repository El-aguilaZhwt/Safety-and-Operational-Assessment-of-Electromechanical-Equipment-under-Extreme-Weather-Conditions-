package com.xinJiangHeTian.electromechanicalEquipment.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto.CurrentWeather;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto.ForecastData;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto.WeatherResponse;
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

/**
 * @author El aguila
 * @version 1.0
 * @description: 天气系统服务层
 * @date 2025/11/10 10:42
 */

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

    //实时气象数据返回
    public CurrentWeather getWeatherData(Double lon, Double lat) throws Exception {
        String weatherUrl = String.format(
                "https://devapi.qweather.com/v7/weather/now?key=%s&location=%s,%s",
                apiKey, lon, lat
        );
        String airUrl = String.format(
                "https://devapi.qweather.com/v7/air/now?key=%s&location=%s,%s",
                apiKey, lon, lat
        );

        CurrentWeather weatherData = new CurrentWeather();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            // 获取天气数据
            HttpGet weatherRequest = new HttpGet(weatherUrl);
            String weatherResponse = EntityUtils.toString(client.execute(weatherRequest).getEntity());
            JsonNode weatherJson = objectMapper.readTree(weatherResponse);

            // 获取空气质量数据
            HttpGet airRequest = new HttpGet(airUrl);
            String airResponse = EntityUtils.toString(client.execute(airRequest).getEntity());
            JsonNode airJson = objectMapper.readTree(airResponse);

            weatherData.setTemperature(weatherJson.path("now").path("temp").asDouble());
            weatherData.setHumidity(weatherJson.path("now").path("humidity").asDouble());
            weatherData.setWindSpeed(weatherJson.path("now").path("windSpeed").asDouble());
            weatherData.setPm25(airJson.path("now").path("pm2p5").asDouble());
            weatherData.setPm10(airJson.path("now").path("pm10").asDouble());

        } catch (Exception e) {
            weatherData.setTemperature(-1.0);
            weatherData.setHumidity(-1.0);
            weatherData.setWindSpeed(-1.0);
            weatherData.setPm25(-1.0);
            weatherData.setPm10(-1.0);
        }

        return weatherData;
    }
    //通过和风天气api返回未来七日天气预报数据
    public List<ForecastData> getSevenDayWeather(Double lon, Double lat) throws Exception {
        String weatherUrl = String.format(
                "https://devapi.qweather.com/v7/weather/7d?key=%s&location=%s,%s",
                apiKey, lon, lat
        );


        List<ForecastData> forecastDataList = new ArrayList<>();

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(weatherUrl);
            String response = EntityUtils.toString(client.execute(request).getEntity());
            JsonNode json = objectMapper.readTree(response);

            JsonNode daily = json.path("daily");
            for (JsonNode day : daily) {
                ForecastData forecastData = new ForecastData();
                forecastData.setDate(day.path("fxDate").asText());
                forecastData.setTemperatureMin(day.path("tempMin").asDouble());
                forecastData.setTemperatureMax(day.path("tempMax").asDouble());
                forecastData.setHumidity(day.path("humidity").asDouble());
                forecastData.setWindSpeed(day.path("windSpeedDay").asDouble());
                forecastData.setWindDirection(Integer.parseInt(day.path("wind360Day").asText()));
                forecastDataList.add(forecastData);
            }
        } catch (Exception e) {
            // 处理异常
        }

        return forecastDataList;
    }
    //
    public Map<String, Double> getCoordinatesByCounty(String county) {
        return countyCoordinates.get(county);
    }

    public Map<String, Double> getInitialWeatherData() {
        return countyCoordinates.get("和田市");
    }

    //获取七日气象数据
    public WeatherResponse getForecastByCoordinate(double lon, double lat) throws Exception {
        WeatherResponse response = new WeatherResponse();
        // 根据坐标获取七日气象数据
        List<ForecastData> forecastDataList = getSevenDayWeather(lon,lat);
        response.setForecastData(forecastDataList);
        return response;
    }
}
