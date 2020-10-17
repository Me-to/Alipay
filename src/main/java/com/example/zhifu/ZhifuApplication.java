package com.example.zhifu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.zhifu.pay.mapper")
public class ZhifuApplication {

    public static void main(String[] args) {
        SpringApplication.run(ZhifuApplication.class, args);
    }

}
