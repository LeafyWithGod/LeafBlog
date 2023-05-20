package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-18 21:01:32
 */
@Repository
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> getListPerms(@Param("id") Long id,@Param("status") String status);

    List<Menu> getAdmin(@Param("menu") String menu, @Param("button") String button, @Param("status") String status);

    List<Menu> getListRouters(@Param("id") Long userId, @Param("status")String menuZero
            ,@Param("menu") String menu, @Param("button")String button);
}
