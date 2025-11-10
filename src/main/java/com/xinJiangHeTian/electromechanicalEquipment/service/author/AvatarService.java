package com.xinJiangHeTian.electromechanicalEquipment.service.author;


import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.Avatar.AvatarInfo;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.Avatar.AvatarStorageResult;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.Avatar.AvatarUploadResult;
import com.xinJiangHeTian.electromechanicalEquipment.entity.User;
import com.xinJiangHeTian.electromechanicalEquipment.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author El aguila
 * @version 1.0
 * @description: 头像使用服务
 * @date 2025/11/6 14:06
 */
@Slf4j
@Service
public class AvatarService {
    @Autowired
    private AvatarStorageService avatarStorageService;

    @Autowired
    private UserMapper userMapper;

    /**
     * 上传并更新用户头像
     */
    @Transactional
    public AvatarUploadResult uploadAvatar(MultipartFile file, Long userId) {
        try {
            // 1. 验证用户存在
            User user = userMapper.findById(userId);
            if (user == null) {
                return AvatarUploadResult.error("用户不存在");
            }

            // 2. 删除旧头像文件
            if (user.getAvatarUrl() != null) {
                avatarStorageService.deleteAvatarFiles(userId, user.getAvatarUrl());
            }

            // 3. 存储新头像
            AvatarStorageResult storageResult = avatarStorageService.storeAvatar(file, userId);

            // 4. 更新数据库
            int updateCount = userMapper.updateAvatar(
                    userId,
                    storageResult.getOriginalUrl(),
                    storageResult.getThumbnailUrl(),
                    storageResult.getOriginalName(),
                    storageResult.getFileSize()
            );

            if (updateCount > 0) {
                log.info("头像上传成功: userId={}, fileSize={}", userId, storageResult.getFileSize());
                return AvatarUploadResult.success(storageResult);
            } else {
                // 数据库更新失败，回滚文件
                avatarStorageService.deleteAvatarFiles(userId, storageResult.getOriginalUrl());
                return AvatarUploadResult.error("头像更新失败");
            }

        } catch (IOException e) {
            log.error("头像上传失败: userId={}, error={}", userId, e.getMessage());
            return AvatarUploadResult.error("头像上传失败: " + e.getMessage());
        } catch (Exception e) {
            log.error("头像上传系统错误: userId={}", userId, e);
            return AvatarUploadResult.error("系统错误: " + e.getMessage());
        }
    }

    /**
     * 删除用户头像
     */
    @Transactional
    public boolean deleteAvatar(Long userId) {
        try {
            User user = userMapper.findById(userId);
            if (user == null) {
                return false;
            }

            // 删除文件
            if (user.getAvatarUrl() != null) {
                avatarStorageService.deleteAvatarFiles(userId, user.getAvatarUrl());
            }

            // 清空数据库记录
            int result = userMapper.updateAvatar(userId, null, null, null, null);
            return result > 0;

        } catch (Exception e) {
            log.error("删除头像失败: userId={}", userId, e);
            return false;
        }
    }

    /**
     * 获取用户头像信息
     */
    public AvatarInfo getAvatarInfo(Long userId) {
        User user = userMapper.findById(userId);
        if (user == null) {
            return null;
        }

        return AvatarInfo.builder()
                .originalUrl(user.getAvatarUrl())
                .thumbnailUrl(user.getAvatarThumbUrl())
                .fileSize(user.getAvatarFileSize())
                .originalName(user.getAvatarOriginalName())
                .updatedAt(user.getAvatarUpdatedAt())
                .build();
    }
}
