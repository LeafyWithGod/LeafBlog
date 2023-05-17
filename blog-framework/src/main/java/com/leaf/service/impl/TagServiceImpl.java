package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Tag;
import com.leaf.mapper.TagMapper;
import com.leaf.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 标签(Tag)表服务实现类
 *
 * @author makejava
 * @since 2023-05-17 21:20:20
 */
@Service("tagService")
public class TagServiceImpl extends ServiceImpl<TagMapper, Tag> implements TagService {

    @Autowired
    private TagMapper tagMapper;

    @Override
    public ResponseResult getList() {
        List<Tag> tagList= tagMapper.getList();
        return ResponseResult.okResult(tagList);
    }
}

