package com.leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Tag;


/**
 * 标签(Tag)表服务接口
 *
 * @author makejava
 * @since 2023-05-17 21:20:20
 */
public interface TagService extends IService<Tag> {

    ResponseResult getList();

}
