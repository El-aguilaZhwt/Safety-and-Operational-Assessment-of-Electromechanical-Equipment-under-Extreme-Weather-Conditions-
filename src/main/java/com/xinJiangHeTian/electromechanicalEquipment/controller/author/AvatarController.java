package com.xinJiangHeTian.electromechanicalEquipment.controller.author;


import com.xinJiangHeTian.electromechanicalEquipment.Dto.ApiResponse;
import com.xinJiangHeTian.electromechanicalEquipment.service.author.AvatarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author El aguila
 * @version 1.0
 * @description: 头像更新，删除，获取控制层
 * @date 2025/11/6 15:10
 */
@Slf4j
@RestController
@RequestMapping("/avatar")
public class AvatarController {
    @Autowired
    private AvatarService avatarService;

    /**
     * 上传头像
     */
    @PostMapping("/upload")
    public ApiResponse<?> uploadAvatar(
            @RequestParam("avatar") MultipartFile file,
            @RequestParam Long userId) {

        log.info("收到头像上传请求: userId={}, fileSize={}, contentType={}",
                userId, file.getSize(), file.getContentType());

        var result = avatarService.uploadAvatar(file, userId);

        if (result.isSuccess()) {
            return ApiResponse.success(result.getMessage(), result.getData());
        } else {
            return ApiResponse.error(result.getMessage());
        }
    }

    /**
     * 删除头像
     */
    @DeleteMapping
    public ApiResponse<String> deleteAvatar(@RequestParam Long userId) {
        boolean success = avatarService.deleteAvatar(userId);

        if (success) {
            return ApiResponse.success("头像删除成功");
        } else {
            return ApiResponse.error("头像删除失败");
        }
    }

    /**
     * 获取头像信息
     */
    @GetMapping
    public ApiResponse<?> getAvatarInfo(@RequestParam Long userId) {
        var avatarInfo = avatarService.getAvatarInfo(userId);

        if (avatarInfo != null) {
            return ApiResponse.success("获取成功", avatarInfo);
        } else {
            return ApiResponse.error("头像信息不存在");
        }
    }
}
