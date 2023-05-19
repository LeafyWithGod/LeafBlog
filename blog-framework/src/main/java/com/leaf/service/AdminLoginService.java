package com.leaf.service;

import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.User;
import com.leaf.domain.vo.AdminUserInfoVo;

public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();

    ResponseResult<AdminUserInfoVo> getInfo();
}
