package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Article;
import com.leaf.domain.entity.Category;
import com.leaf.domain.vo.CategoryVo;
import com.leaf.mapper.ArticleMapper;
import com.leaf.mapper.CategoryMapper;
import com.leaf.service.CategoryService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.constants.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-05-03 22:16:53
 */
@Service()
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ArticleMapper articleMapper;

    @Override
    public ResponseResult getCategoryList() {
        //查询文章表
        List<Article> articleList = articleMapper.hotArticleList(ResultStatus.ArticleZero);
        //获取文章id，去重
        Set<Long> ids = articleList.stream().map(a -> a.getCategoryId()).collect(Collectors.toSet());
        //查询分类表
        List<Category> category=categoryMapper.getCategoryList(ids,ResultStatus.CategoryZero);
        //封装vo
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(category, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

