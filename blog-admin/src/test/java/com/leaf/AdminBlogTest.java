package com.leaf;

import com.leaf.constants.ResultStatus;
import com.leaf.domain.entity.Menu;
import com.leaf.domain.vo.MenusVo;
import com.leaf.mapper.MenuMapper;
import com.leaf.service.MenuService;
import com.leaf.service.RoleService;
import com.leaf.service.impl.MenuServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AdminBlogTest {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private MenuMapper menuMapper;
    @Test
    void roleTest(){
        List<String> list = roleService.selectRoleKeyById(5L);
        System.out.println(list);
    }

    @Test
    void menuTest(){
        List<String> listMenu = menuService.getMenuByid(1L);
        System.out.println(listMenu);
    }

    @Test
    void ListMenuTest(){
        List<Menu> listRouters = menuMapper.getListRouters(5L, ResultStatus.MenuZero
                , ResultStatus.MENU, ResultStatus.BUTTON);
        for (Menu m:listRouters)
        System.out.println(m);
    }

    @Test
    void getMenusVoList(){
        List<Menu> listRouters = menuMapper.getAdmin(ResultStatus.MENU,ResultStatus.BUTTON,ResultStatus.MenuZero);
        MenuServiceImpl menuService = new MenuServiceImpl();
        List<MenusVo> menus = menuService.menusVoList(listRouters);
        for (MenusVo m:menus)
            System.out.println(m);
    }



}
