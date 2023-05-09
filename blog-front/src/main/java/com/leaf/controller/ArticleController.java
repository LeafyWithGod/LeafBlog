package com.leaf.controller;

import com.leaf.domain.ResponseResult;
import com.leaf.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    /**
     * 获取文章浏览量并选出前十排序
     * @return
     */
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticleList() {
        return articleService.hotArticleList();
    }

    /**
     * 获取文章简洁信息
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @GetMapping("/articleList")
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId) {
        return articleService.articleList(pageNum,pageSize,categoryId);
    }

    /**
     * 获取单个文章详情信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseResult selectArticleOne(@PathVariable Long id){
        return articleService.selectArticleOne(id);
    }


}
