package com.leaf.controller;

import com.leaf.annotation.SystemLog;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.User;
import com.leaf.domain.vo.AdminUserInfoVo;
import com.leaf.service.AdminLoginService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping()
public class LoginController {

    @Autowired
    private AdminLoginService adminLoginService;

    @SystemLog(businessName ="后台用户登录")
    @ApiOperation(value = "后台用户登录")
    @PostMapping("/user/login")
    public ResponseResult LoginUser(@RequestBody User user){
        return adminLoginService.login(user);
    }

    @SystemLog(businessName ="获取用户权限及角色")
    @ApiOperation(value = "获取用户权限及角色")
    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfoVo(){
        return adminLoginService.getInfo();
    }

}
