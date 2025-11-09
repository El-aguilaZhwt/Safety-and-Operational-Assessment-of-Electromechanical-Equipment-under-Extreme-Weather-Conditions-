package com.xinJiangHeTian.electromechanicalEquipment.Dto.author.Avatar;

import lombok.Builder;
import lombok.Data;

/**
 * @author El aguila
 * @version 1.0
 * @description:头像存储结果类
 * @date 2025/11/6 14:05
 */
@Data
@Builder
public class AvatarStorageResult {
    private String originalUrl;      // 原图访问URL
    private String thumbnailUrl;    // 缩略图URL
    private Long fileSize;          // 文件大小
    private String originalName;    // 原文件名
    private Integer width;          // 图片宽度
    private Integer height;         // 图片高度
}
