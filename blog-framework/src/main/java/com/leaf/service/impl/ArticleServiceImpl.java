package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Article;
import com.leaf.domain.vo.*;
import com.leaf.mapper.ArticleMapper;
import com.leaf.mapper.CategoryMapper;
import com.leaf.service.ArticleService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.constants.ResultStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 获取文章浏览量并选出前十排序
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {
        List<Article> articleList=articleMapper.hotArticleList(ResultStatus.ArticleZero);
        //使用stream流来进行数据封装操作
//        List<HotArticleVo> hotArticleVos = articleList.stream().map(article -> {
//            HotArticleVo hotArticleVo = new HotArticleVo(article);
//            return hotArticleVo;
//        }).collect(Collectors.toList());
        //改用工具类
        List<HotArticleVo> hotArticleVos = BeanCopyUtils.copyBeanList(articleList, HotArticleVo.class);
        return ResponseResult.okResult(hotArticleVos);
    }

    /**
     * 获取文章简洁信息
     * @param pageNum
     * @param pageSize
     * @param categoryId
     * @return
     */
    @Override
    public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId) {
        //获取分页数据
        List<Article> articles = articleMapper
                .pageArticleList((pageNum - 1) * pageSize, pageSize,categoryId, ResultStatus.ArticleZero);
        //获取分类ids键值对
        List<Long> ids = articles.stream().map(a -> a.getCategoryId()).collect(Collectors.toList());
        List<CategoryVo> categoryListMap = categoryMapper.getCategoryMap(ids,ResultStatus.CategoryZero);
        //把结果封装成map集合
        HashMap<Long, String> categoryMap = new HashMap<>();
        for(CategoryVo categoryVo:categoryListMap){
            categoryMap.put(categoryVo.getId(),categoryVo.getName());
        }
        /**
         * 1.把Article对象封装成ArticleListVo对象
         * 2.封装的时候根据每个Article对象的categoryId去map里面获取categoryName
         */
        List<ArticleListVo> articleListVos = articles.stream().map(article -> {
            ArticleListVo articleListVo = new ArticleListVo();
            BeanUtils.copyProperties(article, articleListVo);
            articleListVo.setCategoryName(categoryMap.get(article.getCategoryId()));
            return articleListVo;
        }).collect(Collectors.toList());
        //封装返回
        PageVo pageVo = new PageVo(articleListVos, new Long(articleListVos.size()));
        return ResponseResult.okResult(pageVo);
    }

    /**
     * 获取单个文章详情信息
     * @param id
     * @return
     */
    @Override
    public ResponseResult selectArticleOne(Long id) {
        Article article=articleMapper.selectArticleOne(id,ResultStatus.ArticleZero);
        ArticleDetalVo articleDetalVo = BeanCopyUtils.copyBean(article, ArticleDetalVo.class);
        String categoryName = categoryMapper.getCategoryName(id,ResultStatus.CategoryZero);
        if (categoryName!=null&&categoryName!="") {
            articleDetalVo.setCategoryName(categoryName);
        }
        return ResponseResult.okResult(articleDetalVo);
    }


}
