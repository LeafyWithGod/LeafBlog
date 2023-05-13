package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.dto.UserDto;
import com.leaf.domain.entity.User;
import com.leaf.domain.vo.UserInfoVo;
import com.leaf.mapper.UserMapper;
import com.leaf.service.UserService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;


/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-05-08 17:01:27
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUser(String username, String status) {
        return userMapper.getUser(username, status);
    }

    @Override
    public List<UserDto> getNikeName(Set<Long> id, String status) {
        return userMapper.getNikeName(id,status);
    }

    @Override
    public ResponseResult getLoginUser() {
        Long userId = SecurityUtils.getUserId();
        User user=userMapper.getLoginUser(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }
}

