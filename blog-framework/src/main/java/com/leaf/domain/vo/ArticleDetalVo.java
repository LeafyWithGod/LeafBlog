package com.leaf.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetalVo extends ArticleListVo {
    //所属分类id
    private Long categoryId;
    //文章内容
    private String content;
}
