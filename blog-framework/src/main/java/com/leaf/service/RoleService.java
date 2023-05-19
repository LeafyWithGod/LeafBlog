package com.leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-05-19 20:38:38
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyById(Long id);
}
