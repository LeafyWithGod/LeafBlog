package com.leaf.domain.vo;

import com.leaf.domain.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Article响应浏览量前十封装字段
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotArticleVo {
    private Long id;
    //标题
    private String title;
    //访问量
    private Long viewCount;
    //构造函数封装
    public HotArticleVo(Article article) {
        this.id = article.getId();
        this.title = article.getTitle();
        this.viewCount = article.getViewCount();
    }

}
