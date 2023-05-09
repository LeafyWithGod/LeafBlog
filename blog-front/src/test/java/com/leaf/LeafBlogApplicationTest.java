package com.leaf;

import com.leaf.domain.entity.Article;
import com.leaf.mapper.ArticleMapper;
import com.leaf.constants.ResultStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class LeafBlogApplicationTest {
    @Autowired
    private ArticleMapper articleMapper;

    @Test
    public void articleList(){
        List<Article> articleList = articleMapper.hotArticleList(ResultStatus.ArticleZero);
        System.out.println(articleList);
    }

    @Test
    public void articleOne(){
        Article articleList = articleMapper.selectArticleOne(1L,ResultStatus.ArticleZero);
        System.out.println(articleList);
    }

}
