package com.leaf.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leaf.domain.ResponseResult;
import com.leaf.domain.entity.Link;
import com.leaf.domain.vo.LinkVo;
import com.leaf.mapper.LinkMapper;
import com.leaf.service.LinkService;
import com.leaf.utils.BeanCopyUtils;
import com.leaf.constants.ResultStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(LeafLink)表服务实现类
 *
 * @author makejava
 * @since 2023-05-05 22:12:44
 */
@Service("leafLinkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Autowired
    private LinkMapper linkMapper;

    @Override
    public ResponseResult selectLink() {
        //查询所有通过的审核
        List<Link> linkList=linkMapper.selectLinkList(ResultStatus.LinkZero);
        //转换成vo
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
        //返回
        return ResponseResult.okResult(linkVos);
    }
}

