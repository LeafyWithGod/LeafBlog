package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Comment;
import com.leaf.domain.vo.CommentVo;
import com.leaf.domain.vo.PageVo;
import com.leaf.mapper.CommentMapper;
import com.leaf.service.CommentService;
import com.leaf.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author makejava
 * @since 2023-05-09 20:34:59
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
        List<Comment> commentList = commentMapper.getCommentList(articleId ,-1L , pageNum, pageSize);
        List<CommentVo> commentVoList = BeanCopyUtils.copyBeanList(commentList, CommentVo.class);
        return ResponseResult.okResult(new PageVo(commentVoList,new Long(commentList.size())));
    }
}

