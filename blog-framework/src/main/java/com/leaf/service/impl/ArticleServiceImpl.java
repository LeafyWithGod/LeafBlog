package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.constants.ResultStatus;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Article;
import com.leaf.domain.vo.*;
import com.leaf.mapper.ArticleMapper;
import com.leaf.mapper.CategoryMapper;
import com.leaf.service.ArticleService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.utils.RedisCache;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper,Article> implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private RedisCache redisCache;

    @Value("${redis.HotArticle}")
    private String key;

    /**
     * 获取文章浏览量并选出前十排序
     * @return
     */
    @Override
    public ResponseResult hotArticleList() {
        List<Article> articleList=articleMapper.hotArticleList(ResultStatus.ArticleZero);
        Map<String, Integer> Map = redisCache.getCacheMap(key);
        for (Article article:articleList){
            //把id转为String
            String id = article.getId().toString();
            article.setViewCount(Map.get(id).longValue());
        }
        //封装
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
        //对浏览量进行操作
        Map<String, Integer> Map = redisCache.getCacheMap(key);
        for (Article article:articles){
            //把id转为String
            String id = article.getId().toString();
            article.setViewCount(Map.get(id).longValue());
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
        PageVo pageVo = new PageVo(articleListVos, (long)articleListVos.size());
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
        //对浏览量进行操作
        Map<String, Integer> Map = redisCache.getCacheMap(key);
        String articleId = article.getId().toString();
        article.setViewCount(Map.get(articleId).longValue());
        //封装
        ArticleDetalVo articleDetalVo = BeanCopyUtils.copyBean(article, ArticleDetalVo.class);
        String categoryName = categoryMapper.getCategoryName(id,ResultStatus.CategoryZero);
        if (categoryName!=null&&categoryName!="") {
            articleDetalVo.setCategoryName(categoryName);
        }
        return ResponseResult.okResult(articleDetalVo);
    }

    /**
     * 更新文章浏览量
     * @param id
     * @return
     */
    @Override
    public ResponseResult updateViewCount(Long id) {
        redisCache.incrementCacheMapValue(key,id.toString(),1);
        return ResponseResult.okResult();
    }

    /**
     * 获取所有文章浏览量
     * @return
     */
    @Override
    public List<HotArticleVo> HotArticleVo() {
        return articleMapper.selectHotArticleVo(ResultStatus.ArticleZero);
    }


}
