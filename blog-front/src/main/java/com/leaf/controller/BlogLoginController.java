package com.leaf.controller;

import com.leaf.annotation.SystemLog;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.User;
import com.leaf.enums.AppHttpCodeEnum;
import com.leaf.exception.SystemException;
import com.leaf.service.BlogLoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping()
@Api(tags = "登录",description = "登录相关接口")
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @ApiOperation(value = "登录接口")
    @SystemLog(businessName = "登录接口")
    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @ApiOperation(value = "退出登录接口")
    @SystemLog(businessName = "退出登录接口")
    @PostMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }

}
