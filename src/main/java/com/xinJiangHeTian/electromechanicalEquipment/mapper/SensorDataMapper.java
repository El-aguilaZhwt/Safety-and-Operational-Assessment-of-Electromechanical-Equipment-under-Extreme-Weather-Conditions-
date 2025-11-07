package com.xinJiangHeTian.electromechanicalEquipment.mapper;

import com.xinJiangHeTian.electromechanicalEquipment.entity.SensorData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SensorDataMapper {
    @Select("SELECT * FROM sensor_data ORDER BY timestamp DESC LIMIT 1")
    SensorData findLatest();

    @Select("SELECT * FROM sensor_data ORDER BY timestamp DESC LIMIT 20")
    List<SensorData> findRecent20();

    @Insert("INSERT INTO sensor_data(temperature, humidity, wind_speed, pm25, pm10, timestamp) " +
            "VALUES(#{temperature}, #{humidity}, #{windSpeed}, #{pm25}, #{pm10}, #{timestamp})")
    void insert(SensorData data);
}
