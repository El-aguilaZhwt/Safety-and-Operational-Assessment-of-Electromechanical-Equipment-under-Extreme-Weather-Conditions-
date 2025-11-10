package com.xinJiangHeTian.electromechanicalEquipment.mapper.deviceMapper;

import com.xinJiangHeTian.electromechanicalEquipment.entity.DeviceUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author El aguila
 * @version 1.0
 * @description: 设备用户关联表Mapper
 * @date 2025/11/10 12:11
 */
@Mapper
public interface DeviceUserMapper {

    // 插入设备用户关联
    int insertDeviceUser(DeviceUser deviceUser);

    // 根据设备ID和用户ID查询关联
    DeviceUser selectDeviceUserByDeviceIdAndUserId(@Param("deviceId") Long deviceId, @Param("userId") Long userId);

    // 检查用户是否已经添加了设备
    boolean existsDeviceUser(@Param("deviceId") Long deviceId, @Param("userId") Long userId);

    // 删除设备用户关联
    int deleteDeviceUser(@Param("deviceId") Long deviceId, @Param("userId") Long userId);

    // 根据设备ID删除所有关联
    int deleteDeviceUserByDeviceId(Long deviceId);

    // 查询设备的所有用户关联
    List<DeviceUser> selectDeviceUsersByDeviceId(Long deviceId);

    // 查询用户的所有设备关联
    List<DeviceUser> selectDeviceUsersByUserId(Long userId);
}