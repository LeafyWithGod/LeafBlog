package com.leaf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.dto.UserDto;
import com.leaf.domain.entity.User;
import com.leaf.domain.vo.UserInfoVo;
import com.leaf.enums.AppHttpCodeEnum;
import com.leaf.exception.SystemException;
import com.leaf.mapper.UserMapper;
import com.leaf.service.UserService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User getUser(String username, String status) {
        return userMapper.getUser(username, status);
    }

    @Override
    public List<UserDto> getNikeName(Set<Long> id, String status) {
        return userMapper.getNikeName(id,status);
    }

    /**
     * 获取用户信息
     * @return
     */
    @Override
    public ResponseResult getLoginUser() {
        Long userId = SecurityUtils.getUserId();
        User user=userMapper.getLoginUser(userId);
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    /**
     * 修改用户信息
     * @param user
     * @return
     */
    @Override
    public ResponseResult updateUser(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public ResponseResult register(User user) {
        //对用户进行非空判断
        if(!StringUtils.hasText(user.getUserName())
                &&!StringUtils.hasText(user.getPassword())
                &&!StringUtils.hasText(user.getEmail())
                &&!StringUtils.hasText(user.getNickName())
        ) {
            throw new SystemException(AppHttpCodeEnum.NOT_NULL);
        }
        //对数据进行校验，查询数据库是否有重复用户
        if (userNameExist(user.getUserName())){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        if (userNickExist(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }
        //加密
        String encode = passwordEncoder.encode(user.getPassword());
        user.setPassword(encode);
        //对数据库进行存入
        save(user);
        return ResponseResult.okResult();
    }

    private boolean userNameExist(String userName) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getUserName,userName);
        return count(lqw)>0;
    }

    private boolean userNickExist(String nickName) {
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getNickName,nickName);
        return count(lqw)>0;
    }
}

