package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.beans.factory.annotation.Configurable;

/**
 * @ClassName SentinelRequestParserConfig
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/4/15 23:27
 * @Version 1.0
 **/
@Configurable
public class SentinelRequestParserConfig {

    public RequestOriginParser requestOriginParser(){
        return (httpServletRequest -> httpServletRequest.getHeader("token"));
    }
}
