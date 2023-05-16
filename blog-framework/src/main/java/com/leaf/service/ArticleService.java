package com.leaf.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Article;
import com.leaf.domain.vo.HotArticleVo;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ArticleService extends IService<Article> {
    @Transactional
    ResponseResult hotArticleList();
    @Transactional
    ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId);
    @Transactional
    ResponseResult selectArticleOne(Long id);

    ResponseResult updateViewCount(Long id);

    List<HotArticleVo> HotArticleVo();
}
