package com.xinJiangHeTian.electromechanicalEquipment.Dto.author.Avatar;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AvatarInfo {
    private String originalUrl;
    private String thumbnailUrl;
    private Long fileSize;
    private String originalName;
    private LocalDateTime updatedAt;
}
