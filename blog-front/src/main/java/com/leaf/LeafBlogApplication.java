package com.leaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.leaf.mapper")
@EnableScheduling
@EnableSwagger2
public class LeafBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(LeafBlogApplication.class,args);
    }
}
