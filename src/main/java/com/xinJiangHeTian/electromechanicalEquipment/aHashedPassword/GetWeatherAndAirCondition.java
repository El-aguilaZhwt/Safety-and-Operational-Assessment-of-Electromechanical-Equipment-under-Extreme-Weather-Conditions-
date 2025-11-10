package com.xinJiangHeTian.electromechanicalEquipment.aHashedPassword;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDto.ForecastData;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GetWeatherAndAirCondition {
    @Value("${weather.api.key}")
    private static String apiKey;

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Map<String, Map<String, Double>> countyCoordinates = Map.of(
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


    //通过和风天气api返回未来七日天气预报数据
    public static List<ForecastData> getSevenDayWeather(Double lon, Double lat) throws Exception {
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

    public static void main(String[] args) throws Exception {
        Map<String,Double> coordians = countyCoordinates.get("和田市");
//        System.out.println("精度:"+coordians.get("lon")+"维度:"+coordians.get("lat"));
        System.out.println(getSevenDayWeather(coordians.get("lon"),coordians.get("lat")));
    }
}
