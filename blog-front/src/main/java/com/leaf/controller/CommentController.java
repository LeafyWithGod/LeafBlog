package com.leaf.controller;

import com.leaf.annotation.SystemLog;
import com.leaf.constants.ResultStatus;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Comment;
import com.leaf.service.CommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
@Api(tags = "评论",description = "评论相关接口")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 获取文章评论
     * @param articleId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取文章评论")
    @SystemLog(businessName = "获取文章评论")
    @GetMapping("/commentList")
    public ResponseResult getCommentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.getCommentList(articleId,pageNum,pageSize,ResultStatus.ARTICLE_COMMENT);
    }

    /**
     * 添加评论
     * @param comment
     * @return
     */
    @ApiOperation(value = "添加评论")
    @SystemLog(businessName = "添加评论")
    @PostMapping()
    public ResponseResult addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    /**
     * 获取友链评论
     * @param pageNum
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "获取友链评论")
    @SystemLog(businessName = "获取友链评论")
    @GetMapping("/linkCommentList")
    public ResponseResult getLinkCommentList(Long articleId,Integer pageNum,Integer pageSize){
        return commentService.getCommentList(articleId,pageNum,pageSize,ResultStatus.LINK_COMMENT);
    }

}
