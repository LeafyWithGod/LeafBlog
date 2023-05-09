package com.leaf.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Link;
import org.springframework.transaction.annotation.Transactional;

/**
 * 友链(LeafLink)表服务接口
 *
 * @author makejava
 * @since 2023-05-05 22:11:41
 */
public interface LinkService extends IService<Link> {

    @Transactional
    ResponseResult selectLink();
}
