package com.gb.preferredMallManage;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@MapperScan("com.gb.preferredMallManage.dao")
@EnableWebMvc
public class PreferredMallManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(PreferredMallManageApplication.class, args);
    }

}
