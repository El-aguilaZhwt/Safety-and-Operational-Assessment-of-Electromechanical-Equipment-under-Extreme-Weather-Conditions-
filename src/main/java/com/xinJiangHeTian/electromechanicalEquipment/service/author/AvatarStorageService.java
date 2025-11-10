package com.xinJiangHeTian.electromechanicalEquipment.service.author;

import com.xinJiangHeTian.electromechanicalEquipment.Dto.authorDto.Avatar.AvatarStorageResult;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

/**
 * @author El aguila
 * @version 1.0
 * @description:文件存储服务实现——头像存储服务
 * @date 2025/11/6 14:01
 */
@Service
@Slf4j
public class AvatarStorageService {
    @Value("${app.file.upload-dir}")
    private String uploadDir;

    @Value("${app.file.base-url}")
    private String baseUrl;

    @Value("${app.avatar.max-width}")
    private int maxWidth;

    @Value("${app.avatar.max-height}")
    private int maxHeight;

    @Value("${app.avatar.thumbnail-width}")
    private int thumbWidth;

    @Value("${app.avatar.thumbnail-height}")
    private int thumbHeight;

    @Value("${app.avatar.quality}")
    private double quality;

    private static final String[] ALLOWED_MIME_TYPES = {
            "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    };

    /**
     * 存储用户头像
     */
    public AvatarStorageResult storeAvatar(MultipartFile file, Long userId) throws IOException {
        // 1. 验证文件
        validateAvatarFile(file);

        // 2. 创建存储目录
        Path avatarDir = createAvatarDirectory(userId);

        // 3. 生成唯一文件名
        String fileExtension = getFileExtension(file.getOriginalFilename());
        String baseFileName = UUID.randomUUID().toString();
        String originalFileName = baseFileName + "." + fileExtension;
        String thumbFileName = baseFileName + "_thumb.jpg";

        // 4. 处理原图（调整大小）
        Path originalPath = avatarDir.resolve(originalFileName);
        BufferedImage processedImage = processOriginalImage(file, originalPath);

        // 5. 生成缩略图
        Path thumbPath = avatarDir.resolve(thumbFileName);
        generateThumbnail(processedImage, thumbPath);

        // 6. 构建返回结果
        return AvatarStorageResult.builder()
                .originalUrl(baseUrl + "/avatars/" + userId + "/" + originalFileName)
                .thumbnailUrl(baseUrl + "/avatars/" + userId + "/" + thumbFileName)
                .fileSize(Files.size(originalPath))
                .originalName(file.getOriginalFilename())
                .width(processedImage.getWidth())
                .height(processedImage.getHeight())
                .build();
    }

    /**
     * 验证头像文件
     */
    private void validateAvatarFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("上传的文件为空");
        }

        if (file.getSize() > 5 * 1024 * 1024) { // 5MB
            throw new IOException("文件大小不能超过5MB");
        }

        String contentType = file.getContentType();
        boolean validType = false;
        for (String allowedType : ALLOWED_MIME_TYPES) {
            if (allowedType.equalsIgnoreCase(contentType)) {
                validType = true;
                break;
            }
        }

        if (!validType) {
            throw new IOException("不支持的文件格式: " + contentType);
        }

        // 验证图片内容
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            throw new IOException("无效的图片文件");
        }
    }

    /**
     * 创建头像存储目录
     */
    private Path createAvatarDirectory(Long userId) throws IOException {
        Path userAvatarDir = Paths.get(uploadDir, "avatars", userId.toString());
        Files.createDirectories(userAvatarDir);
        return userAvatarDir;
    }

    /**
     * 处理原图（调整大小、压缩）
     */
    private BufferedImage processOriginalImage(MultipartFile file, Path outputPath) throws IOException {
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // 计算缩放比例
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();

        if (originalWidth <= maxWidth && originalHeight <= maxHeight) {
            // 无需缩放，直接保存
            Files.copy(file.getInputStream(), outputPath, StandardCopyOption.REPLACE_EXISTING);
            return originalImage;
        }

        // 计算缩放尺寸
        double widthRatio = (double) maxWidth / originalWidth;
        double heightRatio = (double) maxHeight / originalHeight;
        double ratio = Math.min(widthRatio, heightRatio);

        int newWidth = (int) (originalWidth * ratio);
        int newHeight = (int) (originalHeight * ratio);

        // 缩放并保存
        Thumbnails.of(originalImage)
                .size(newWidth, newHeight)
                .outputFormat("jpg")
                .outputQuality(quality)
                .toFile(outputPath.toFile());

        return Thumbnails.of(originalImage)
                .size(newWidth, newHeight)
                .asBufferedImage();
    }

    /**
     * 生成缩略图
     */
    private void generateThumbnail(BufferedImage image, Path outputPath) throws IOException {
        Thumbnails.of(image)
                .size(thumbWidth, thumbHeight)
                .outputFormat("jpg")
                .outputQuality(0.8)
                .toFile(outputPath.toFile());
    }

    /**
     * 删除用户头像文件
     */
    public boolean deleteAvatarFiles(Long userId, String avatarUrl) {
        try {
            if (avatarUrl == null || !avatarUrl.startsWith(baseUrl)) {
                return false;
            }

            // 从URL中提取文件路径
            String relativePath = avatarUrl.substring(baseUrl.length());
            Path filePath = Paths.get(uploadDir, relativePath);

            // 删除原图
            boolean deletedOriginal = Files.deleteIfExists(filePath);

            // 删除缩略图（假设缩略图路径规则）
            Path thumbPath = Paths.get(filePath.getParent().toString(),
                    filePath.getFileName().toString().replace(".", "_thumb."));
            boolean deletedThumb = Files.deleteIfExists(thumbPath);

            log.info("删除头像文件: 原图={}, 缩略图={}", deletedOriginal, deletedThumb);
            return deletedOriginal || deletedThumb;

        } catch (IOException e) {
            log.error("删除头像文件失败: userId={}, url={}", userId, avatarUrl, e);
            return false;
        }
    }

    /**
     * 清理用户的所有头像文件
     */
    public void cleanupUserAvatars(Long userId) {
        try {
            Path userAvatarDir = Paths.get(uploadDir, "avatars", userId.toString());
            if (Files.exists(userAvatarDir)) {
                Files.walk(userAvatarDir)
                        .sorted((a, b) -> -a.compareTo(b)) // 反向排序，先删除文件再删除目录
                        .forEach(path -> {
                            try {
                                Files.deleteIfExists(path);
                            } catch (IOException e) {
                                log.warn("删除文件失败: {}", path, e);
                            }
                        });
                log.info("清理用户头像目录: userId={}", userId);
            }
        } catch (IOException e) {
            log.error("清理用户头像目录失败: userId={}", userId, e);
        }
    }

    private String getFileExtension(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
}
