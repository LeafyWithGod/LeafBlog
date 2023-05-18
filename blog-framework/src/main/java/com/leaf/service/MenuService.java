package com.leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-05-18 21:01:32
 */
public interface MenuService extends IService<Menu> {

    List<String> getListMenu(Long id);
}
