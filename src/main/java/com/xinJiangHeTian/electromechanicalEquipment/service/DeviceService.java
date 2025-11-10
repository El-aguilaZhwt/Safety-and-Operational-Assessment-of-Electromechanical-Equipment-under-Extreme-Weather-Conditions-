package com.xinJiangHeTian.electromechanicalEquipment.service;

import com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto.CreateDeviceRequest;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto.DeviceDto;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto.UpdateDeviceRequest;
import com.xinJiangHeTian.electromechanicalEquipment.entity.Device;
import com.xinJiangHeTian.electromechanicalEquipment.entity.DeviceUser;
import com.xinJiangHeTian.electromechanicalEquipment.mapper.deviceMapper.DeviceMapper;
import com.xinJiangHeTian.electromechanicalEquipment.mapper.deviceMapper.DeviceUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DeviceService {

    @Autowired
    private DeviceMapper deviceMapper;

    @Autowired
    private DeviceUserMapper deviceUserMapper;

    /**
     * 创建新设备
     */
    public DeviceDto createDevice(CreateDeviceRequest request) {
        // 验证区间
        validateRanges(request);

        Device device = new Device();
        device.setName(request.getName());
        device.setMinTemperature(request.getMinTemperature());
        device.setMaxTemperature(request.getMaxTemperature());
        device.setMinHumidity(request.getMinHumidity());
        device.setMaxHumidity(request.getMaxHumidity());
        device.setMinWindSpeed(request.getMinWindSpeed());
        device.setMaxWindSpeed(request.getMaxWindSpeed());
        device.setIsPublic(request.getIsPublic());
        device.setCreatedBy(request.getUserId());

        deviceMapper.insertDevice(device);

        // 创建设备用户关联
        DeviceUser deviceUser = new DeviceUser();
        deviceUser.setDeviceId(device.getId());
        deviceUser.setUserId(request.getUserId());
        deviceUser.setIsOwner(true);
        deviceUserMapper.insertDeviceUser(deviceUser);

        return convertToDTO(device, true);
    }

    /**
     * 更新设备信息
     */
    public DeviceDto updateDevice(Long deviceId, UpdateDeviceRequest request) {
        Device device = deviceMapper.selectDeviceById(deviceId);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        // 检查用户权限
        checkOwnerPermission(deviceId, request.getUserId());

        // 验证区间
        validateRanges(request);

        device.setName(request.getName());
        device.setMinTemperature(request.getMinTemperature());
        device.setMaxTemperature(request.getMaxTemperature());
        device.setMinHumidity(request.getMinHumidity());
        device.setMaxHumidity(request.getMaxHumidity());
        device.setMinWindSpeed(request.getMinWindSpeed());
        device.setMaxWindSpeed(request.getMaxWindSpeed());

        if (request.getIsPublic() != null) {
            device.setIsPublic(request.getIsPublic());
        }

        deviceMapper.updateDevice(device);
        return convertToDTO(device, true);
    }

    /**
     * 删除设备
     */
    public void deleteDevice(Long deviceId, Long userId) {
        Device device = deviceMapper.selectDeviceById(deviceId);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        // 检查用户权限
        checkOwnerPermission(deviceId, userId);

        // 删除关联记录
        deviceUserMapper.deleteDeviceUserByDeviceId(deviceId);
        // 删除设备
        deviceMapper.deleteDevice(deviceId);
    }

    /**
     * 获取用户的所有设备
     */
    public List<DeviceDto> getUserDevices(Long userId) {
        List<Device> devices = deviceMapper.selectDevicesByUserId(userId);
        return devices.stream()
                .map(device -> {
                    DeviceUser deviceUser = deviceUserMapper.selectDeviceUserByDeviceIdAndUserId(device.getId(), userId);
                    boolean isOwner = deviceUser != null && Boolean.TRUE.equals(deviceUser.getIsOwner());
                    return convertToDTO(device, isOwner);
                })
                .collect(Collectors.toList());
    }

    /**
     * 获取公开设备列表
     */
    public List<DeviceDto> getPublicDevices() {
        List<Device> devices = deviceMapper.selectPublicDevices();
        return devices.stream()
                .map(device -> convertToDTO(device, false))
                .collect(Collectors.toList());
    }

    /**
     * 添加公开设备到我的设备
     */
    public void addPublicDeviceToUser(Long deviceId, Long userId) {
        Device device = deviceMapper.selectDeviceById(deviceId);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        if (!Boolean.TRUE.equals(device.getIsPublic())) {
            throw new RuntimeException("该设备不是公开设备");
        }

        if (deviceUserMapper.existsDeviceUser(deviceId, userId)) {
            throw new RuntimeException("您已经添加了该设备");
        }

        if (userId.equals(device.getCreatedBy())) {
            throw new RuntimeException("不能添加自己创建的设备");
        }

        DeviceUser deviceUser = new DeviceUser();
        deviceUser.setDeviceId(deviceId);
        deviceUser.setUserId(userId);
        deviceUser.setIsOwner(false);
        deviceUserMapper.insertDeviceUser(deviceUser);
    }

    /**
     * 从我的设备中移除设备
     */
    public void removeDeviceFromUser(Long deviceId, Long userId) {
        DeviceUser deviceUser = deviceUserMapper.selectDeviceUserByDeviceIdAndUserId(deviceId, userId);
        if (deviceUser == null) {
            throw new RuntimeException("设备不存在于您的设备列表中");
        }

        if (Boolean.TRUE.equals(deviceUser.getIsOwner())) {
            throw new RuntimeException("不能移除自己创建的设备，请使用删除功能");
        }

        deviceUserMapper.deleteDeviceUser(deviceId, userId);
    }

    /**
     * 获取设备详情
     */
    public DeviceDto getDeviceDetail(Long deviceId, Long userId) {
        Device device = deviceMapper.selectDeviceById(deviceId);
        if (device == null) {
            throw new RuntimeException("设备不存在");
        }

        // 检查访问权限
        boolean hasAccess = deviceUserMapper.existsDeviceUser(deviceId, userId)
                || Boolean.TRUE.equals(device.getIsPublic());

        if (!hasAccess) {
            throw new RuntimeException("无权限查看该设备");
        }

        DeviceUser deviceUser = deviceUserMapper.selectDeviceUserByDeviceIdAndUserId(deviceId, userId);
        boolean isOwner = deviceUser != null && Boolean.TRUE.equals(deviceUser.getIsOwner());

        return convertToDTO(device, isOwner);
    }

    /**
     * 验证区间参数,创建
     */
    private void validateRanges(CreateDeviceRequest request) {
        if (request.getMinTemperature().compareTo(request.getMaxTemperature()) > 0) {
            throw new IllegalArgumentException("最低温度不能大于最高温度");
        }
        if (request.getMinHumidity().compareTo(request.getMaxHumidity()) > 0) {
            throw new IllegalArgumentException("最低湿度不能大于最高湿度");
        }
        if (request.getMinWindSpeed().compareTo(request.getMaxWindSpeed()) > 0) {
            throw new IllegalArgumentException("最低风速不能大于最高风速");
        }
    }

    /**
     * 验证区间参数,更新(方法重载)
     */
    private void validateRanges(UpdateDeviceRequest request) {
        if (request.getMinTemperature().compareTo(request.getMaxTemperature()) > 0) {
            throw new IllegalArgumentException("最低温度不能大于最高温度");
        }
        if (request.getMinHumidity().compareTo(request.getMaxHumidity()) > 0) {
            throw new IllegalArgumentException("最低湿度不能大于最高湿度");
        }
        if (request.getMinWindSpeed().compareTo(request.getMaxWindSpeed()) > 0) {
            throw new IllegalArgumentException("最低风速不能大于最高风速");
        }
    }

    /**
     * 检查用户是否是设备所有者
     */
    private void checkOwnerPermission(Long deviceId, Long userId) {
        DeviceUser deviceUser = deviceUserMapper.selectDeviceUserByDeviceIdAndUserId(deviceId, userId);
        if (deviceUser == null || !Boolean.TRUE.equals(deviceUser.getIsOwner())) {
            throw new RuntimeException("只有设备所有者可以执行此操作");
        }
    }

    /**
     * 转换实体为DTO
     */
    private DeviceDto convertToDTO(Device device, boolean isOwner) {
        DeviceDto dto = new DeviceDto();
        dto.setId(device.getId());
        dto.setName(device.getName());
        dto.setMinTemperature(device.getMinTemperature());
        dto.setMaxTemperature(device.getMaxTemperature());
        dto.setMinHumidity(device.getMinHumidity());
        dto.setMaxHumidity(device.getMaxHumidity());
        dto.setMinWindSpeed(device.getMinWindSpeed());
        dto.setMaxWindSpeed(device.getMaxWindSpeed());
        dto.setIsPublic(device.getIsPublic());
        dto.setIsOwner(isOwner);
        dto.setCreatedTime(LocalDateTime.now());
        return dto;
    }
}