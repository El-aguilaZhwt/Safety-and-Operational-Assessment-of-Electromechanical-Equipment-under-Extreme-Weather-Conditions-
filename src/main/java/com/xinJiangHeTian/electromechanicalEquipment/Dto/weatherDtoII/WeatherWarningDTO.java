package com.xinJiangHeTian.electromechanicalEquipment.Dto.weatherDtoII;

import lombok.Data;

/**
 * 气象预警信息数据传输对象（DTO）
 * 用于封装和传输气象部门发布的天气预警信息
 */
@Data
public class WeatherWarningDTO {
    /**
     * 预警标题
     * 示例："暴雨橙色预警"、"大风黄色预警"
     */
    private String title;

    /**
     * 预警类型
     * 示例：暴雨、大风、高温、寒潮、雷电等
     */
    private String type;

    /**
     * 预警等级
     * 标准等级：蓝色、黄色、橙色、红色
     * 颜色顺序表示严重程度递增
     */
    private String level;

    /**
     * 预警状态
     * 示例：生效中、已解除、已过期
     */
    private String status;

    /**
     * 预警开始时间
     * 格式：YYYY-MM-DD HH:mm:ss
     */
    private String startTime;

    /**
     * 预警结束时间
     * 格式：YYYY-MM-DD HH:mm:ss
     */
    private String endTime;

    /**
     * 预警详细内容
     * 包含具体的预警说明、影响范围、防御指南等详细信息
     */
    private String text;

    // Getters and Setters (由Lombok @Data注解自动生成)
}