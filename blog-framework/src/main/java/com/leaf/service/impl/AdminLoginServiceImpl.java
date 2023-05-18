package com.leaf.service.impl;

import com.leaf.domain.ResponseResult;
import com.leaf.domain.dto.LoginUser;
import com.leaf.domain.entity.User;
import com.leaf.service.AdminLoginService;
import com.leaf.utils.JwtUtil;
import com.leaf.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class AdminLoginServiceImpl implements AdminLoginService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private RedisCache redisCache;
    @Value("${redis.redisLogin}")
    private String blogLogin;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名或密码错误");
        }
        //获取username生成token
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        String userName = principal.getUser().getUserName();
        String jwt = JwtUtil.createJWT(userName);
        //写入redis,60s*60一小时过期
        redisCache.setCacheObject(this.blogLogin + userName, principal, 60 * 60, TimeUnit.SECONDS);
        HashMap<String, String> map = new HashMap<>();
        map.put("token", jwt);
        //返回
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        //获取token，并解析
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        //获取username
        String userName = principal.getUser().getUserName();
        //删除redis里面的角色
        redisCache.deleteObject(this.blogLogin + userName);
        return ResponseResult.okResult();
    }
}

