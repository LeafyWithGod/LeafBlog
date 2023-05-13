package com.leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.dto.UserDto;
import com.leaf.domain.entity.User;

import java.util.List;
import java.util.Set;


/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-05-08 17:00:37
 */
public interface UserService extends IService<User> {
    User getUser(String username,String status);
    List<UserDto> getNikeName(Set<Long> id, String status);

    ResponseResult getLoginUser();
}
