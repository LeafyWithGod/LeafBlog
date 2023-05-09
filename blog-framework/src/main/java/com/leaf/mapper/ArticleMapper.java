package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.Article;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    List<Article> hotArticleList(String status);

    List<Article> pageArticleList(
            @Param("pageNum") Integer pageNum
            , @Param("pageSize") Integer pageSize
            , @Param("categoryId") Long categoryId
            , @Param("status") String status
    );

    Article selectArticleOne(@Param("id") Long id,@Param("status") String status);
}
