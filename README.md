# Safety-and-Operational-Assessment-of-Electromechanical-Equipment-under-Extreme-Weather-Conditions-of-Hetian-XinJiang/backend
新疆和田地区极端气候下的机电设备安全运行评估系统后端部分
# 新疆和田地区极端气候下的机电设备安全运行评估系统后端部分

> Xinjiang Hotan Region Electromechanical Equipment Safety Assessment System under Extreme Climate

[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)](https://spring.io/)

## 🌟 项目简介

基于数字孪生技术的极端气候机电设备安全运行监测与评估平台。系统实时监控新疆和田地区机电设备运行状态，结合气象数据进行智能分析和预警，为高海拔、多风沙、极端温差环境下的设备安全提供科学保障。

## 📊 接口文档
# 新疆和田地区机电设备极端天气安全评估系统 - 接口文档

## 项目信息

| 项目 | 内容 |
|------|------|
| 系统名称 | 机电设备极端天气安全评估系统 |
| 版本 | v1.0.0 |
| 基础URL | http://localhost:8080/api |
| 文档日期 | 2025-10-19 |
| 技术栈 | Spring Boot 3.5.7 + MyBatis + MySQL + Java 21 |

## 目录

1. [认证接口](#1-认证接口-authentication)
2. [天气数据接口](#2-天气数据接口-weatherdata)
3. [用户身份管理接口](#3-用户身份管理接口-usermodify)
4. [设备管理接口](#4-设备管理接口-devicemanage)
5. [安全评估接口](#5-安全评估接口-securityevaluation)
6. [文件上传接口](#6-文件上传接口-fileupload)

---

## 1. 认证接口 (Authentication)

### 1.1 用户注册

**接口说明**: 新用户注册

- **请求URL**: POST `/auth/register`
- **请求头**:
Content-Type: application/json
- **请求体**:
```json
{
"username": "string, required, 3-20位",
"email": "string, required, 邮箱格式",
"password": "string, required, 6-20位",
"confirmPassword": "string, required",
"bio": "string, optional, 个人简介"
}
```
- **成功响应(200)**:
```json
{
"success": true,
"message": "注册成功",
"data": {
"success": true,
"message": "注册成功",
"user": {
"id": 1,
"username": "testuser",
"email": "test@example.com",
"role": "USER",
"bio": "摄影爱好者"
}
},
"timestamp": "2024-01-01T10:00:00.000",
"code": 200
}
```
- **错误响应(400)**:
```json
{
"success": false,
"message": "用户名已存在",
"data": null,
"timestamp": "2024-01-01T10:00:00.000",
"code": 400
}
```
### 1.2 用户登录

**接口说明**: 用户登录获取JWT令牌（用户身份分为GUEST、USER、ADMIN）

- **请求URL**: POST `/auth/login`
- **请求头**:
Content-Type: application/json
- **请求体**:
```json
{
"username": "string, required",
"password": "string, required",
"role": "string, required"
}
```
- **成功响应(200)**:
```json
{
"success": true,
"message": "登录成功",
"data": {
"token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
"tokenType": "Bearer",
"expiresIn": 86399000,
"user": {
"id": 1,
"username": "admin",
"email": "admin@jingangroup.com",
"role": "ADMIN",
"avatarUrl": null,
"bio": "系统管理员"
}
},
"timestamp": "2024-01-01T10:00:00.000",
"code": 200
}
```
### 1.3 注册并自动登录

**接口说明**: 注册成功后自动登录

- **请求URL**: POST `/auth/register-login`
- **请求头**:
Content-Type: application/json
- **请求体**: 同注册接口
- **响应**: 同登录接口响应格式

### 1.4 验证令牌

**接口说明**: 验证JWT令牌有效性

- **请求URL**: GET `/auth/validate`
- **请求头**:
Authorization: Bearer {token}
- **成功响应(200)**:
```json
{
"success": true,
"message": "令牌有效",
"data": true,
"timestamp": "2024-01-01T10:00:00.000",
"code": 200
}
```
---

## 2. 天气数据接口 (WeatherData)

### 2.1 县实时气象数据查询

**接口说明**: 每个县实时气象数据查询

- **请求URL**: POST `/weather/county-data`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"county": "县名称"
}
```
- **成功响应(200)**:
```json
{
"success": true,
"message": "实时数据响应成功",
"data": {
"currentWeather": {
"temperature": 2.0,
"humidity": 24.0,
"windSpeed": 3.0,
"pm25": 133.0,
"pm10": 304.0
},
"forecastData": null
},
"code": 200,
"timestamp": "2025-11-08T23:38:41.213105600"
}
```
### 2.2 县初始实时气象数据查询

**接口说明**: 初始加载的县实时气象数据查讯（默认和田县）

- **请求URL**: POST `/weather/initial-data`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**: 无

- **成功响应(200)**:
```json
{
"success": true,
"message": "实时数据响应成功",
"data": {
"currentWeather": {
"temperature": 2.0,
"humidity": 24.0,
"windSpeed": 3.0,
"pm25": 133.0,
"pm10": 304.0
},
"forecastData": null
},
"code": 200,
"timestamp": "2025-11-08T23:38:41.213105600"
}
```
### 2.3 未来七天气象数据查询（经纬度方法）

**接口说明**: 用经纬度查询未来七天气象数据

- **请求URL**: GET `/weather/forecast`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| lon | string 或 number | 是 | 目标位置的经度（Longitude），例如：116.4074 |
| lat | string 或 number | 是 | 目标位置的纬度（Latitude），例如：39.9042 |

- **成功响应(200)**:
```json
{
"success": true,
"message": "成功",
"data": {
"currentWeather": null,
"forecastData": [
{
"date": "2025-11-09",
"temperatureMin": -3.0,
"temperatureMax": 11.0,
"humidity": 21.0,
"windSpeed": 3.0,
"windDirection": 0
},
{
"date": "2025-11-10",
"temperatureMin": -3.0,
"temperatureMax": 13.0,
"humidity": 18.0,
"windSpeed": 3.0,
"windDirection": 45
},
{
"date": "2025-11-11",
"temperatureMin": -2.0,
"temperatureMax": 16.0,
"humidity": 20.0,
"windSpeed": 3.0,
"windDirection": 45
},
{
"date": "2025-11-12",
"temperatureMin": -2.0,
"temperatureMax": 12.0,
"humidity": 20.0,
"windSpeed": 3.0,
"windDirection": 315
},
{
"date": "2025-11-13",
"temperatureMin": -2.0,
"temperatureMax": 14.0,
"humidity": 19.0,
"windSpeed": 3.0,
"windDirection": 90
},
{
"date": "2025-11-14",
"temperatureMin": -1.0,
"temperatureMax": 15.0,
"humidity": 16.0,
"windSpeed": 3.0,
"windDirection": 45
},
{
"date": "2025-11-15",
"temperatureMin": -2.0,
"temperatureMax": 12.0,
"humidity": 17.0,
"windSpeed": 3.0,
"windDirection": 315
}
]
},
"code": 200,
"timestamp": "2025-11-09T10:04:46.653006500"
}
```
### 2.4 未来七天气象数据查询（县名称方法）

**接口说明**: 用经纬度查询未来七天气象数据

- **请求URL**: GET `/weather/forecast-county`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"county": "县名称"
}
```
- **成功响应（200）**: 同2.3成功响应

---

## 3. 用户身份管理接口 (UserModify)

### 3.1 用户密码修改

**接口说明**: 用用户输入的新密码替换旧密码

- **请求URL**: PUT `/user/password-modify`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"currentUserId": "newuser",
"currentPassword": "newpass123",
"newPassword": "newpass1234"
}
```
- **成功响应（200）**:
```json
{
"success": true,
"message": "密码修改成功",
"data": null,
"code": 200,
"timestamp": "2025-11-09T22:49:20.345663500"
}
```
### 3.2 用户邮箱修改

**接口说明**: 用用户输入的新邮箱替换旧邮箱

- **请求URL**: PUT `/user/email-modify`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"userId": 11,
"password": "newpass123",
"newEmail": "new@example.com"
}
```
- **成功响应（200）**:
```json
{
"success": true,
"message": "email修改成功",
"data": null,
"code": 200,
"timestamp": "2025-11-09T23:28:18.252426500"
}
```
### 3.3 用户身份修改

**接口说明**: 修改用户角色

- **请求URL**: PUT `/user/role-modify`
 **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"userId": "11",
"password": "newpass123",
"newRole": "ADMIN"
}
```
- **成功响应（200）**:
```json
{
"success": true,
"message": "email修改成功",
"data": null,
"code": 200,
"timestamp": "2025-11-09T23:43:14.653424"
}
```
---

## 4. 设备管理接口 (DeviceManage)

### 4.1 创建设备

**接口说明**: 创建新的设备，创建者自动成为设备所有者

- **请求URL**: POST `/device`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"name": "温室大棚设备",
"userId": 123,
"minTemperature": 15.0,
"maxTemperature": 30.0,
"minHumidity": 40.0,
"maxHumidity": 80.0,
"minWindSpeed": 0.0,
"maxWindSpeed": 20.0,
"isPublic": true
}
```
- **成功响应（200）**:
```json
{
"success": true,
"message": "设备创建成功",
"data": {
"id": 2,
"name": "温室大棚设备",
"minTemperature": 15.0,
"maxTemperature": 30.0,
"minHumidity": 40.0,
"maxHumidity": 80.0,
"minWindSpeed": 0.0,
"maxWindSpeed": 20.0,
"isPublic": true,
"isOwner": true,
"createdTime": "2025-11-10T14:19:29.4387618"
},
"code": 200,
"timestamp": "2025-11-10T14:19:29.454229100"
}
```
### 4.2 更新设备信息

**接口说明**: 更新设备信息（仅设备所有者可操作）

- **请求URL**: PUT `/device/{deviceId}`
- **路径参数**:
  - `deviceId`: 设备ID
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"userId": "11",
"name": "更新后的设备名称",
"minTemperature": 10.0,
"maxTemperature": 35.0,
"minHumidity": 35.0,
"maxHumidity": 85.0,
"minWindSpeed": 0.0,
"maxWindSpeed": 25.0,
"isPublic": true
}
```
- **成功响应（200）**:
```json
{
"success": true,
"message": "成功",
"data": {
"id": 2,
"name": "更新后的设备名称",
"minTemperature": 10.0,
"maxTemperature": 35.0,
"minHumidity": 35.0,
"maxHumidity": 85.0,
"minWindSpeed": 0.0,
"maxWindSpeed": 25.0,
"isPublic": true,
"isOwner": true,
"createdTime": "2025-11-10T14:26:39.4791187"
},
"code": 200,
"timestamp": "2025-11-10T14:26:39.484485400"
}
```
### 4.3 删除设备

**接口说明**: 删除设备及其所有关联（仅设备所有者可操作）

- **请求URL**: DELETE `/device/{deviceId}/{userId}`
- **路径参数**:
  - `deviceId`: 设备ID
  - `userId`: 用户ID
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**: 无

- **成功响应（200）**:
```json
{
"success": true,
"message": "设备删除成功",
"data": null,
"code": 200,
"timestamp": "2025-11-10T14:31:28.306070200"
}
```
### 4.4 获取我的设备

**接口说明**: 获取当前用户拥有的所有设备（包括自己创建的和添加的公开设备）

- **请求URL**: GET `/device/my/{userId}`
- **路径参数**:
  - `userId`: 用户ID
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**: 无

- **成功响应（200）**:
```json
{
"success": true,
"message": "我的设备列表获取成功",
"data": [
{
"id": 3,
"name": "温室大棚设备",
"minTemperature": 15.00,
"maxTemperature": 30.00,
"minHumidity": 40.00,
"maxHumidity": 80.00,
"minWindSpeed": 0.00,
"maxWindSpeed": 20.00,
"isPublic": true,
"isOwner": true,
"createdTime": "2025-11-10T14:35:43.1774727"
},
{
"id": 4,
"name": "温室大棚设备2",
"minTemperature": 15.00,
"maxTemperature": 30.00,
"minHumidity": 40.00,
"maxHumidity": 80.00,
"minWindSpeed": 0.00,
"maxWindSpeed": 20.00,
"isPublic": true,
"isOwner": true,
"createdTime": "2025-11-10T14:35:43.178488"
}
],
"code": 200,
"timestamp": "2025-11-10T14:35:43.179475"
}
```
### 4.5 获取公开设备

**接口说明**: 获取所有公开的设备

- **请求URL**: GET `/device/public`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**: 无

- **成功响应（200）**:
```json
{
"success": true,
"message": "我的设备列表获取成功",
"data": [
{
"id": 3,
"name": "温室大棚设备",
"minTemperature": 15.00,
"maxTemperature": 30.00,
"minHumidity": 40.00,
"maxHumidity": 80.00,
"minWindSpeed": 0.00,
"maxWindSpeed": 20.00,
"isPublic": true,
"isOwner": true,
"createdTime": "2025-11-10T14:35:43.1774727"
},
{
"id": 4,
"name": "温室大棚设备2",
"minTemperature": 15.00,
"maxTemperature": 30.00,
"minHumidity": 40.00,
"maxHumidity": 80.00,
"minWindSpeed": 0.00,
"maxWindSpeed": 20.00,
"isPublic": true,
"isOwner": true,
"createdTime": "2025-11-10T14:35:43.178488"
}
],
"code": 200,
"timestamp": "2025-11-10T14:35:43.179475"
}
```
### 4.6 添加设备到我的

**接口说明**: 将公开设备添加到当前用户的设备列表中

- **请求URL**: POST `/device/add-to-my`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"deviceId": 5,
"userId": 123
}
```
（注意userId是当前用户的id而不是设备主人的id）
- **成功响应（200）**:
```json
{
"success": true,
"message": "成功",
"data": null,
"code": 200,
"timestamp": "2025-11-10T14:43:48.680134300"
}
```
### 4.7 从我的移除设备

**接口说明**: 从当前用户的设备列表中移除设备（不能移除自己创建的设备）

- **请求URL**: DELETE `/device/remove-from-my`
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**:
```json
{
"deviceId": 5,
"userId": 123
}
```
- **成功响应（200）**:
```json
{
"success": true,
"message": "成功",
"data": null,
"code": 200,
"timestamp": "2025-11-10T14:48:38.337744900"
}
```
### 4.8 获取设备详情

**接口说明**: 获取设备详细信息（需要有访问权限：设备所有者、已添加用户或设备公开）

- **请求URL**: GET `/device/{deviceId}/{userId}`
- **路径参数**:
  - `deviceId`: 设备ID
  - `userId`: 用户ID
- **请求头**:
Content-Type: application/json
Authorization: Bearer {token}
- **请求体**: 无

- **成功响应（200）**:
```json
{
"success": true,
"message": "成功",
"data": {
"id": 5,
"name": "公开设备A",
"minTemperature": 15.00,
"maxTemperature": 30.00,
"minHumidity": 40.00,
"maxHumidity": 80.00,
"minWindSpeed": 0.00,
"maxWindSpeed": 20.00,
"isPublic": true,
"isOwner": true,
"createdTime": "2025-11-10T14:52:01.1018626"
},
"code": 200,
"timestamp": "2025-11-10T14:52:01.101862600"
}
```
---

## 5. 安全评估接口 (SecurityEvaluation)

### 5.1 暂未开发

---

## 6. 文件上传接口 (FileUpload)

### 6.1 暂未开发

## 📈 技术栈

### 后端技术
- **框架**：Spring Boot 3.x
- **ORM**：MyBatis 
- **数据库**：MySQL 8.0
- **安全**：Spring Security
- **缓存**：Redis
- **API文档**：Swagger/OpenAPI 3.0


## 🌍 应用场景

- **电力设备监控**：变电站、输电线路在极端气候下的安全运行
- **水利设施管理**：泵站、闸门等设备的防冻、防风沙保护
- **交通基础设施**：隧道通风、照明设备的气候适应性评估
- **工业设备维护**：矿山、工厂关键设备的预防性维护
