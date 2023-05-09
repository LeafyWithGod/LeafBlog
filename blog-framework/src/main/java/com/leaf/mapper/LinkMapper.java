package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.Link;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 友链(LeafLink)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-05 22:13:26
 */
@Repository
public interface LinkMapper extends BaseMapper<Link> {

    List<Link> selectLinkList(String status);
}
