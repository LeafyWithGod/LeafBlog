package com.leaf;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.leaf.mapper")
public class blogAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(blogAdminApplication.class,args);
    }
}
