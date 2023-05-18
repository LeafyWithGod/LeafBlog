package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.Menu;
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

    List<String> getListMenu(Long id);
}
