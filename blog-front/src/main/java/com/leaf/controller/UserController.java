package com.leaf.controller;

import com.leaf.annotation.SystemLog;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.User;
import com.leaf.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Api(tags = "用户",description = "用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取用户信息")
    @SystemLog(businessName = "获取用户信息")
    @GetMapping("/userInfo")
    public ResponseResult getUser(){
        return userService.getLoginUser();
    }

    @ApiOperation(value = "更新用户信息")
    @SystemLog(businessName = "更新用户信息")
    @PutMapping("/userInfo")
    public ResponseResult updateUser(@RequestBody User user){
        return userService.updateUser(user);
    }

    @ApiOperation(value = "用户注册")
    @SystemLog(businessName = "用户注册")
    @PostMapping("/register")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

}
