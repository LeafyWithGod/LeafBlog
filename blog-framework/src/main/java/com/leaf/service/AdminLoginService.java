package com.leaf.service;

import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.User;

public interface AdminLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
