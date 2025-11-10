package com.xinJiangHeTian.electromechanicalEquipment.controller;

import com.xinJiangHeTian.electromechanicalEquipment.Dto.ApiResponse;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto.CreateDeviceRequest;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto.DeviceDto;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto.UpdateDeviceRequest;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.deviceManageDto.UserDeviceManageRequest;
import com.xinJiangHeTian.electromechanicalEquipment.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author El aguila
 * @version 1.0
 * @description: 设备管理控制层
 * @date 2025/11/10 12:39
 */
@RestController
@RequestMapping("/device")
@Validated
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    /**
     * 创建新设备，创建者自动成为设备所有者
     * @param request
     * @return
     */
    @PostMapping
    public ApiResponse<DeviceDto> deviceCreating(@Validated @RequestBody CreateDeviceRequest request){
        //创建响应，将userId汇入DeviceDto
        DeviceDto device = deviceService.createDevice(request);
        return ApiResponse.success("设备创建成功",device);
    }
    /**
     * 更新设备信息
     */
    @PutMapping("/{deviceId}")
    public ApiResponse<DeviceDto> updateDevice(
            @PathVariable Long deviceId,
            @Valid @RequestBody UpdateDeviceRequest request) {
        DeviceDto device = deviceService.updateDevice(deviceId, request);
        return ApiResponse.success(device);
    }
    /**
     * 删除设备 - 使用路径参数更符合RESTful规范
     */
    @DeleteMapping("/{deviceId}/{userId}")
    public ApiResponse<Void> deleteDevice(
            @PathVariable Long deviceId,
            @PathVariable Long userId) {
        deviceService.deleteDevice(deviceId, userId);
        return ApiResponse.success("设备删除成功", null);
    }
    /**
     * 获取我的设备列表 - 修正路径参数注解
     */
    @GetMapping("/my/{userId}")
    public ApiResponse<List<DeviceDto>> getMyDevices(@PathVariable Long userId) {
        List<DeviceDto> devices = deviceService.getUserDevices(userId);
        return ApiResponse.success("我的设备列表获取成功",devices);
    }
    /**
     * 获取公开设备列表
     */
    @GetMapping("/public")
    public ApiResponse<List<DeviceDto>> getPublicDevices() {
        List<DeviceDto> devices = deviceService.getPublicDevices();
        return ApiResponse.success(devices);
    }

    /**
     * 添加公开设备到我的设备
     */
    @PostMapping("/add-to-my")
    public ApiResponse<Void> addPublicDeviceToMy(@Validated @RequestBody UserDeviceManageRequest request) {
        deviceService.addPublicDeviceToUser(request.getDeviceId(), request.getUserId());
        return ApiResponse.success(null);
    }

    /**
     * 从我的设备中移除设备
     */
    @DeleteMapping("/remove-from-my")
    public ApiResponse<Void> removeDeviceFromMy(@Validated @RequestBody UserDeviceManageRequest request) {
        deviceService.removeDeviceFromUser(request.getDeviceId(), request.getUserId());
        return ApiResponse.success(null);
    }

    /**
     * 获取设备详情 - 修正路径参数注解
     */
    @GetMapping("/{deviceId}/{userId}")
    public ApiResponse<DeviceDto> getDeviceDetail(
            @PathVariable Long deviceId,
            @PathVariable Long userId) {
        DeviceDto device = deviceService.getDeviceDetail(deviceId, userId);
        return ApiResponse.success(device);
    }

}
