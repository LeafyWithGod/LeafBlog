package com.leaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.leaf.mapper")
@EnableScheduling
public class LeafBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeafBlogApplication.class,args);
    }
}
