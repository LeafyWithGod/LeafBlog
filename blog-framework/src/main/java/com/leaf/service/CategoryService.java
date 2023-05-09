package com.leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Category;
import org.springframework.transaction.annotation.Transactional;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-05-03 22:16:53
 */
public interface CategoryService extends IService<Category> {

    @Transactional
    ResponseResult getCategoryList();
}
