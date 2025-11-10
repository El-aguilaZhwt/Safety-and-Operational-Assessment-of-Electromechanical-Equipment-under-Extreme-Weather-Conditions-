-- 创建数据库
CREATE DATABASE IF NOT EXISTS weather_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 创建用户表
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password VARCHAR(200) NOT NULL,
    identity VARCHAR(50) NOT NULL DEFAULT 'visitor',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 设备表
CREATE TABLE IF NOT EXISTS device (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '设备ID',
    name VARCHAR(100) NOT NULL COMMENT '设备名称',
    min_temperature DECIMAL(5,2) NOT NULL COMMENT '最低适宜温度',
    max_temperature DECIMAL(5,2) NOT NULL COMMENT '最高适宜温度',
    min_humidity DECIMAL(5,2) NOT NULL COMMENT '最低适宜湿度',
    max_humidity DECIMAL(5,2) NOT NULL COMMENT '最高适宜湿度',
    min_wind_speed DECIMAL(5,2) NOT NULL COMMENT '最低适宜风速',
    max_wind_speed DECIMAL(5,2) NOT NULL COMMENT '最高适宜风速',
    is_public BOOLEAN DEFAULT FALSE COMMENT '是否公开',
    created_by BIGINT NOT NULL COMMENT '创建者用户ID',
    created_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_time DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_public (is_public),
    INDEX idx_created_by (created_by)
) COMMENT '设备信息表';

-- 设备用户关联表
CREATE TABLE IF NOT EXISTS device_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '关联ID',
    device_id BIGINT NOT NULL COMMENT '设备ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    is_owner BOOLEAN DEFAULT FALSE COMMENT '是否为设备所有者',
    added_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
    UNIQUE KEY uk_device_user (device_id, user_id),
    INDEX idx_user_id (user_id),
    FOREIGN KEY (device_id) REFERENCES device(id) ON DELETE CASCADE
) COMMENT '设备用户关联表';

-- 创建传感器数据表
CREATE TABLE IF NOT EXISTS sensor_data (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    temperature DOUBLE,
    humidity DOUBLE,
    wind_speed DOUBLE,
    pm25 DOUBLE,
    pm10 DOUBLE,
    timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建处理方案表
CREATE TABLE IF NOT EXISTS solutions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    solution_type VARCHAR(50),
    content TEXT,
    uploader VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 创建Spring Session表（Spring Boot会自动创建）