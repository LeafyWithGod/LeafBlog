package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.dto.UserDto;
import com.leaf.domain.entity.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-08 17:00:37
 */
@Repository
public interface UserMapper extends BaseMapper<User> {
    User getUser(@Param("username") String username,@Param("status") String status);
    List<UserDto> getNikeName(@Param("ids") Set<Long> id, @Param("status") String status);

    User getLoginUser(@Param("id") Long userId);
}
