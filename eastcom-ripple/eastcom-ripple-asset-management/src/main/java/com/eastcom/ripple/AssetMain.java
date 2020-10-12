package com.eastcom.ripple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Create By User on 2020/9/3
 */
@SpringBootApplication
@MapperScan(basePackages = {"com.eastcom.ripple.mapper"})
public class AssetMain {
    public static void main(String[] args) {
        SpringApplication.run(AssetMain.class,args);
    }
}
