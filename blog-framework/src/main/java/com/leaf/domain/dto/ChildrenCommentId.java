package com.leaf.domain.dto;

import com.leaf.domain.vo.CommentVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildrenCommentId {
    private Long id;
    private CommentVo Children;
}
