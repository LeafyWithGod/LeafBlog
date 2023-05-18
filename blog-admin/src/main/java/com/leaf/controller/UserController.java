package com.leaf.controller;

import com.leaf.annotation.SystemLog;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.User;
import com.leaf.service.AdminLoginService;
import com.leaf.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AdminLoginService adminLoginService;

    @SystemLog(businessName ="后台用户登录")
    @ApiOperation(value = "后台用户登录")
    @PostMapping("/login")
    public ResponseResult LoginUser(@RequestBody User user){
        return adminLoginService.login(user);
    }
}
