package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.Category;
import com.leaf.domain.vo.CategoryVo;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 分类表(Category)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-03 22:16:42
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    List<Category> getCategoryList(@Param("ids") Set<Long> ids,@Param("status") String status);

    List<CategoryVo> getCategoryMap(@Param("ids")List<Long> ids,@Param("status") String status);

    String getCategoryName(@Param("id") Long id,@Param("status") String status);
}
