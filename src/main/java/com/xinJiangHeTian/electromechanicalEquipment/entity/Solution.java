package com.xinJiangHeTian.electromechanicalEquipment.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author El aguila
 * @version 1.0
 * @description: 解决方案表
 * @date 2025/11/8 00:04
 */
/**
 * 解决方案实体类
 * 用于存储和管理针对极端气候条件下机电设备安全运行的解决方案
 */
@Data
public class Solution {
    /**
     * 解决方案主键ID
     * 唯一标识每条解决方案记录
     */
    private Long id;

    /**
     * 解决方案类型
     * 分类标识，用于区分不同类别的解决方案
     * 示例：temperature_control-温度控制, moisture_protection-防潮防湿,
     *        wind_resistance-抗风防护, dust_prevention-防尘措施
     */
    private String solutionType;

    /**
     * 解决方案详细内容
     * 包含具体的技术措施、操作步骤、注意事项等详细信息
     * 支持富文本格式，可包含图文说明
     */
    private String content;

    /**
     * 方案上传者/创建人
     * 记录方案的创建人员或部门信息
     */
    private String uploader;

    /**
     * 方案创建时间
     * 记录方案入库的系统时间
     * 格式：ISO 8601标准格式（yyyy-MM-dd'T'HH:mm:ss）
     */
    private LocalDateTime createdAt;

    /**
     * 实体创建时的回调方法
     * 在创建新解决方案时自动设置创建时间为当前系统时间
     * 通常由JPA的@PrePersist注解触发或在业务逻辑中手动调用
     */
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    // Getters and Setters (由Lombok @Data注解自动生成)
}
