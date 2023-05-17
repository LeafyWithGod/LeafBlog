package com.leaf.job;

import com.leaf.domain.entity.Article;
import com.leaf.service.ArticleService;
import com.leaf.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 定时把redis中的值写入数据库
 */
@Component
public class UpdateViewCountJob {

    @Value("${redis.HotArticle}")
    private String key;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisCache redisCache;

    @Scheduled(cron = "0 0/30 * * * ?")
    public void updateViewCount(){
        SimpleDateFormat format = new SimpleDateFormat("yyy-mm-dd HH:mm:ss");
        Date start = new Date(System.currentTimeMillis());
        System.out.println(format.format(start)+" 定时任务开始执行");
        //获取redis中的map
        Map<String, Integer> Map = redisCache.getCacheMap(key);
        List<Article> collect = Map.entrySet()
                .stream()
                .map(entry -> new Article(Long.parseLong(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //写入数据库
        articleService.updateBatchById(collect);
        Date stop = new Date(System.currentTimeMillis());
        System.out.println(format.format(stop)+" 定时任务执行完成");
    }
}
