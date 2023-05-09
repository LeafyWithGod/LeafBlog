package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-08 17:00:37
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    User getUser(@Param("username") String username,@Param("status") String status);
}
