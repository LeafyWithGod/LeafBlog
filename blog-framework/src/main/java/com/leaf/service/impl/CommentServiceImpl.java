package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.constants.ResultStatus;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.dto.ChildrenCommentId;
import com.leaf.domain.dto.UserDto;
import com.leaf.domain.entity.Comment;
import com.leaf.domain.vo.CommentVo;
import com.leaf.domain.vo.PageVo;
import com.leaf.mapper.CommentMapper;
import com.leaf.service.CommentService;
import com.leaf.service.UserService;
import com.leaf.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

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
    @Autowired
    private UserService userService;

    @Override
    public ResponseResult getCommentList(Long articleId, Integer pageNum, Integer pageSize) {
        //查询根评论
        List<Comment> commentList = commentMapper.getCommentList(articleId ,-1L , pageNum, pageSize);
        List<CommentVo> commentVoList = toCommentVoList(commentList);
        List<CommentVo> commentVoChildrenList=getChildren(commentVoList);
        return ResponseResult.okResult(new PageVo(commentVoChildrenList,new Long(commentList.size())));
    }

    /**
     * 查询子评论并封装进父评论
     * @param commentVoList
     * @return
     */
    private List<CommentVo> getChildren(List<CommentVo> commentVoList) {
        List<Long> ids=new ArrayList<>();
        //获取父评论的id
        for (CommentVo commentVo:commentVoList){
            ids.add(commentVo.getId());
        }
        //查询id对应的子评论
        List<Comment> sonCommentList = commentMapper.getSonCommentList(ids);
        List<CommentVo> sonCommentVos = toCommentVoList(sonCommentList);
        //遍历父评论
        for (CommentVo commentVo:commentVoList){
            List<CommentVo> sonCom=new ArrayList<>();
            //遍历子评论
            for (CommentVo son:sonCommentVos){
                if (son.getRootId()==commentVo.getId())
                    //如果子评论的父id等于父评论的id，就把子评论添加到数组里面
                    sonCom.add(son);
            }
            //赋值
            commentVo.setChildren(sonCom);
        }
        return commentVoList;
    }

    /**
     * 封装
     * @param list
     * @return
     */
    private List<CommentVo> toCommentVoList(List<Comment> list){
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //收集评论人id和所回复的目标评论id
        List<Long> createBys=new ArrayList<>();
        List<Long> toCommentUserIds=new ArrayList<>();
        for (CommentVo commentVo:commentVos){
            createBys.add(commentVo.getCreateBy());
            toCommentUserIds.add(commentVo.getToCommentUserId());
        }
        //查询
        List<UserDto> createByUsers = userService.getNikeName(createBys, ResultStatus.UserZero);
        List<UserDto> toCommentUsers = userService.getNikeName(toCommentUserIds, ResultStatus.UserZero);
        //封装到map里面
        Map<Long,String> createByUserMap=new HashMap<>();
        Map<Long,String> toCommentUserMap=new HashMap<>();
        for (UserDto createByUser:createByUsers){
            createByUserMap.put(createByUser.getId(),createByUser.getUserName());
        }
        for (UserDto toCommentUser:toCommentUsers){
            toCommentUserMap.put(toCommentUser.getId(),toCommentUser.getUserName());
        }
        //封装进commentVos
        for (CommentVo commentVo:commentVos){
            commentVo.setUsername(createByUserMap.get(commentVo.getCreateBy()));
            commentVo.setToCommentUserName(toCommentUserMap.get(commentVo.getToCommentUserId()));
        }
        return commentVos;
    }



}

