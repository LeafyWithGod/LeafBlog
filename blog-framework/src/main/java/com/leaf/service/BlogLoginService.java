package com.leaf.service;

import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.User;
import org.springframework.transaction.annotation.Transactional;

public interface BlogLoginService {
    @Transactional
    ResponseResult login(User user);

    ResponseResult logout();
}
