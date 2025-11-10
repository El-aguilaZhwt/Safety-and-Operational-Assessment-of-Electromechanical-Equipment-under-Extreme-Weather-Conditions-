package com.xinJiangHeTian.electromechanicalEquipment.mapper.deviceMapper;

import com.xinJiangHeTian.electromechanicalEquipment.entity.Device;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author El aguila
 * @version 1.0
 * @description: 设备表mapper
 * @date 2025/11/10 13:06
 */
@Mapper
public interface DeviceMapper {

    // 插入设备
    int insertDevice(Device device);

    // 根据ID查询设备
    Device selectDeviceById(Long id);

    // 更新设备
    int updateDevice(Device device);

    // 删除设备
    int deleteDevice(Long id);

    // 查询用户拥有的设备
    List<Device> selectDevicesByUserId(Long userId);

    // 查询公开设备
    List<Device> selectPublicDevices();

    // 查询用户创建的设备
    List<Device> selectDevicesByCreatedBy(Long createdBy);

    // 检查设备是否存在
    boolean existsDeviceById(Long id);
}