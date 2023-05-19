package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.constants.ResultStatus;
import com.leaf.domain.entity.Menu;
import com.leaf.mapper.MenuMapper;
import com.leaf.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-05-18 21:01:32
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<String> getListMenu(Long id) {
        if (id==1){
            List<Menu> admin = menuMapper.getAdmin(ResultStatus.MENU, ResultStatus.BUTTON, ResultStatus.MenuZero);
            List<String> perms = admin.stream().map(m -> m.getPerms()).collect(Collectors.toList());
            return perms;
        }
        return menuMapper.getListMenu(id,ResultStatus.MenuZero);
    }
}

