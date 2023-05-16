package com.leaf.runner;

import com.leaf.domain.vo.HotArticleVo;
import com.leaf.service.ArticleService;
import com.leaf.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

/**
 * 服务启动时将文章浏览量写入redis
 */
@Component
public class TestRunner implements CommandLineRunner {

    @Value("${redis.HotArticle}")
    private String key;
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ArticleService articleService;

    @Override
    public void run(String... args) throws Exception {
        //查询
        List<HotArticleVo> articleVos=articleService.HotArticleVo();
        //转换
        HashMap<String, Integer> map = new HashMap<>();
        for (HotArticleVo hot:articleVos){
            map.put(hot.getId().toString(),hot.getViewCount().intValue());
        }
        //存入
        redisCache.setCacheMap(key,map);
    }
}
