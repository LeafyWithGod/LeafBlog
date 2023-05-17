package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-17 21:20:20
 */
@Repository
public interface TagMapper extends BaseMapper<Tag> {

    List<Tag> getList();
}
