package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-19 20:38:38
 */
@Repository
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyById(@Param("id") Long id,@Param("status") String status);
}
