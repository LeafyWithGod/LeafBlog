package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.constants.ResultStatus;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Menu;
import com.leaf.domain.vo.MenusVo;
import com.leaf.domain.vo.RoutersVo;
import com.leaf.mapper.MenuMapper;
import com.leaf.service.MenuService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
        if (id!=null&&id.equals(1L)){
            List<Menu> admin = menuMapper.getAdmin(ResultStatus.MENU, ResultStatus.BUTTON, ResultStatus.MenuZero);
            return admin.stream().map(Menu::getPerms).collect(Collectors.toList());

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
        //查询
        List<Menu> menus;
        if (SecurityUtils.isAdmin()){
            menus=menuMapper.getAdmin(ResultStatus.MENU,ResultStatus.BUTTON,ResultStatus.MenuZero);
        }else {
            menus=menuMapper.getListRouters(userId, ResultStatus.MenuZero
                    , ResultStatus.MENU, ResultStatus.BUTTON);
        }
        //封装
        List<MenusVo> menusVos=menusVoList(menus);
        //返回
        return ResponseResult.okResult(new RoutersVo(menusVos));
    }

    /**
     * 将子菜单封装到父菜单
     * 方法一：直接封装
     * 好处：不使用递归，避免服务器高负载
     * @param menus
     * @return
     */
    public List<MenusVo> menusVoList(List<Menu> menus) {
        //排序
        menus.sort((m1, m2) -> {
            return m2.getParentId().intValue() - m1.getParentId().intValue();
        });
        List<MenusVo> menusVos = BeanCopyUtils.copyBeanList(menus, MenusVo.class);
        ArrayList<MenusVo> sonMenusVoList = new ArrayList<>();
        //将子菜单封装进父菜单
        for (MenusVo fath:menusVos){
            List<MenusVo> chi = new ArrayList<>();
            for (MenusVo son:menusVos){
                if (fath.getId().equals(son.getParentId())) {
                    chi.add(son);
                    sonMenusVoList.add(son);
                }
            }
            fath.setChildren(chi);
        }
        //删除子菜单
        for (MenusVo m:sonMenusVoList)
            menusVos.remove(m);
        return menusVos;
    }
    /*
                               m
                              /\
                             m  m
                            /\  /\
                          m  m m  m
                             ...
      //方法二：递归用法
      private List<Menu> builderMenuTree(List<Menu> menus, Long parentId) {
              List<Menu> menuTree = menus.stream()
                      .filter(menu -> menu.getParentId().equals(parentId))
                      .map(menu -> menu.setChildren(getChildren(menu, menus)))
                      .collect(Collectors.toList());
              return menuTree;
          }

           //获取存入参数的 子Menu集合
           //@param menu
           //@param menus
           //@return

     private List<Menu> getChildren(Menu menu, List<Menu> menus) {
     List<Menu> childrenList = menus.stream()
                      .filter(m -> m.getParentId().equals(menu.getId()))
                      .map(m -> m.setChildren(getChildren(m, menus)))
                      .collect(Collectors.toList());
     return childrenList;
     }
     */

}

