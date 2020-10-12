package com.eastcom.ripple.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Hongzh
 * @date 2020/09/21
 * @description 资产模块配置类
 */
@Configuration
public class AssetApplicationConfig {
    //将RestTemplate放到IOC容器中
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
