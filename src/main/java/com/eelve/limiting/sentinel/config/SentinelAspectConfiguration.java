package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.annotation.aspectj.SentinelResourceAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName SentinelAspectConfiguration
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/1/16 20:08
 * @Version 1.0
 **/
@Configuration
public class SentinelAspectConfiguration {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
