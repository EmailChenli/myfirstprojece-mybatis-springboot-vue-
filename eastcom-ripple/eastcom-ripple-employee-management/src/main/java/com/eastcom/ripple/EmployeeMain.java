package com.eastcom.ripple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan({"com.eastcom.ripple.mapper"})
public class EmployeeMain {

    public static void main(String[] args) {
        SpringApplication.run(EmployeeMain.class, args);
    }

}
