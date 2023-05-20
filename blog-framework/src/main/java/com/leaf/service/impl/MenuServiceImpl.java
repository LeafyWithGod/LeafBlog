package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.constants.ResultStatus;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Menu;
import com.leaf.domain.vo.MenusVo;
import com.leaf.mapper.MenuMapper;
import com.leaf.service.MenuService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * 根据id获取权限集合
     * @param id
     * @return
     */
    @Override
    public List<String> getMenuByid(Long id) {
        if (SecurityUtils.isAdmin()){
            List<Menu> admin = menuMapper.getAdmin(ResultStatus.MENU, ResultStatus.BUTTON, ResultStatus.MenuZero);
            List<String> perms = admin.stream().map(m -> m.getPerms()).collect(Collectors.toList());
            return perms;
        }
        return menuMapper.getListPerms(id,ResultStatus.MenuZero);
    }

    /**
     * 根据id获取菜单列表
     * @return
     */
    @Override
    public ResponseResult getListRouters() {
        Long userId = SecurityUtils.getUserId();
        List<Menu> menus = new ArrayList<Menu>();
        if (SecurityUtils.isAdmin()){
            menus=menuMapper.getAdmin(ResultStatus.MENU,ResultStatus.BUTTON,ResultStatus.MenuZero);
        }else {
            menus=menuMapper.getListRouters(userId, ResultStatus.MenuZero
                    , ResultStatus.MENU, ResultStatus.BUTTON);
        }
//        List<MenusVo> menusVos=menusVoList(menus);
        return null;
    }

    /**
     * 将子菜单封装到父菜单
     * @param menus
     * @return
     */
    public List<MenusVo> menusVoList(List<Menu> menus) {
        Collections.sort(menus,(m1,m2)->{
            return m2.getParentId().intValue()-m1.getParentId().intValue();
        });
        List<MenusVo> menusVos = BeanCopyUtils.copyBeanList(menus, MenusVo.class);
        ArrayList<MenusVo> menusVoArrayList = new ArrayList<>();
        for (MenusVo fath:menusVos){
            List<MenusVo> chi = new ArrayList<>();
            for (MenusVo son:menusVos){
                if (fath.getId()==son.getParentId()) {
                    chi.add(son);
                }
            }
            fath.setChildren(chi);
        }
        return menusVoArrayList;
    }
}

