package com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.Avatar;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AvatarUploadResult {
    private boolean success;
    private String message;
    private AvatarStorageResult data;

    public static AvatarUploadResult success(AvatarStorageResult data) {
        return AvatarUploadResult.builder()
                .success(true)
                .message("头像上传成功")
                .data(data)
                .build();
    }

    public static AvatarUploadResult error(String message) {
        return AvatarUploadResult.builder()
                .success(false)
                .message(message)
                .build();
    }
}
