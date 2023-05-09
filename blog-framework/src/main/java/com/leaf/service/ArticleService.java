package com.leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Article;
import org.springframework.transaction.annotation.Transactional;

public interface ArticleService extends IService<Article> {
    @Transactional
    ResponseResult hotArticleList();
    @Transactional
    ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId);
    @Transactional
    ResponseResult selectArticleOne(Long id);
}
