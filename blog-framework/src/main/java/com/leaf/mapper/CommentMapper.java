package com.leaf.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.entity.Comment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * 评论表(Comment)表数据库访问层
 *
 * @author makejava
 * @since 2023-05-09 20:34:59
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {
    List<Comment> getCommentList(@Param("articleId") Long articleId
            ,@Param("rootId") Long rootId
            ,@Param("pageNum") Integer pageNum
            ,@Param("pageSize") Integer pageSize
            ,@Param("type") String type);

    List<Comment> getSonCommentList(@Param("ids") List<Long> ids,@Param("type") String type);
}
