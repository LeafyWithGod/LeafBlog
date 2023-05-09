package com.leaf.service.impl;

import com.leaf.constants.ResultStatus;
import com.leaf.domain.dto.LoginUser;
import com.leaf.domain.entity.User;
import com.leaf.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {


    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //获取并返回
        return getUser(username);
    }

    /**
     * 首先获取redis里面，redis里面没有的再去mysql里面查再写到redis里面
     * @param username
     * @return
     */
    private UserDetails getUser(String username) {
        User user = userMapper.getUser(username, ResultStatus.UserZero);
        if(Objects.isNull(user)){
            throw new RuntimeException("用户不存在");
        }
        //TODO 查询权限信息
        //封装用户信息
        LoginUser loginUser=new LoginUser(user);
        //TODO 封装权限信息
        return loginUser;
    }
}
