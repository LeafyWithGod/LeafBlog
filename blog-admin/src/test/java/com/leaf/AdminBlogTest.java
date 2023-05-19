package com.leaf;

import com.leaf.service.MenuService;
import com.leaf.service.RoleService;
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
    @Test
    void roleTest(){
        List<String> list = roleService.selectRoleKeyById(5L);
        System.out.println(list);
    }

    @Test
    void menuTest(){
        List<String> listMenu = menuService.getListMenu(1L);
        System.out.println(listMenu);
    }

}
