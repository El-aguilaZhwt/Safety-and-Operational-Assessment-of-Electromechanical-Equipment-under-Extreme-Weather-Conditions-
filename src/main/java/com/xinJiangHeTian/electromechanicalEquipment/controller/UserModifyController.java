package com.xinJiangHeTian.electromechanicalEquipment.controller;

import com.xinJiangHeTian.electromechanicalEquipment.Dto.ApiResponse;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.UserModifyDto.EmailModifyRequest;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.UserModifyDto.PasswordModifyRequest;
import com.xinJiangHeTian.electromechanicalEquipment.Dto.UserModifyDto.RoleModifyRequest;
import com.xinJiangHeTian.electromechanicalEquipment.service.UserModifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author El aguila
 * @version 1.0
 * @description: 用户修改密码，邮箱，身份控制层
 * @date 2025/11/9 21:45
 */
@RestController
@RequestMapping("/user")
@Validated
public class UserModifyController {
    @Autowired
    private UserModifyService userModifyService;

    @PutMapping("/password-modify")
    public ApiResponse<String> passwordModify(@Validated @RequestBody PasswordModifyRequest request){
        //先验证当前密码是否输入正确
        boolean isPasswordCorrect = userModifyService.currentPasswordConfirm(request.getCurrentUserId(),request.getCurrentPassword());
        if(!isPasswordCorrect){
            return ApiResponse.error("当前密码输入错误",401);
        }
        //将旧密码更新成新密码，返回success给前端
        boolean passwordModifyOk = userModifyService.passwordModify(request.getCurrentUserId(),request.getNewPassword());
        if(!passwordModifyOk){
            return ApiResponse.error("修改密码失败，请稍后再试",400);
        }
        return ApiResponse.success("密码修改成功",null);
    }

    @PutMapping("/email-modify")
    public ApiResponse<String> emailModify(@Validated @RequestBody EmailModifyRequest request){
        //先验证当前密码是否输入正确
        boolean isPasswordCorrect = userModifyService.currentPasswordConfirm(request.getUserId(),request.getPassword());
        if(!isPasswordCorrect){
            return ApiResponse.error("当前密码输入错误",401);
        }
        //将旧email更新成email，返回success给前端
        boolean emailModifyOk = userModifyService.emailModify(request.getUserId(),request.getNewEmail());
        if(!emailModifyOk){
            return ApiResponse.error("修改密码失败，请稍后再试",400);
        }
        return ApiResponse.success("email修改成功",null);
    }

    @PutMapping("role-modify")
    public ApiResponse<String> roleModify(@Validated @RequestBody RoleModifyRequest request){
        //先验证当前密码是否输入正确
        boolean isPasswordCorrect = userModifyService.currentPasswordConfirm(request.getUserId(),request.getPassword());
        if(!isPasswordCorrect){
            return ApiResponse.error("当前密码输入错误",401);
        }
        //将旧role更新成新role，返回success给前端
        boolean roleModifyOk = userModifyService.roleModify(request.getUserId(),request.getNewRole());
        if(!roleModifyOk){
            return ApiResponse.error("修改密码失败，请稍后再试",400);
        }
        return ApiResponse.success("email修改成功",null);
    }
}
