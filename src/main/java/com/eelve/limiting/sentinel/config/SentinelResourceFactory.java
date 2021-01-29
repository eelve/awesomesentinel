package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName SentinelResourceInit
 * @Description 用于初始化的时候获取所有sentinel 资源
 * @Author zhao.zhilue
 * @Date 2021/1/29 20:31
 * @Version 1.0
 **/
@Component
public class SentinelResourceFactory implements BeanPostProcessor {

    private static final Set<String> SENTINEL_RESOURCE = new HashSet();

    public static Set<String> getSentinelResource() {
        return SENTINEL_RESOURCE;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Method[] methods = bean.getClass().getDeclaredMethods();
        for (Method method : methods) {
            SentinelResource sentinelResource = AnnotationUtils.findAnnotation(method, SentinelResource.class);
            if (!Objects.isNull(sentinelResource)){
                SENTINEL_RESOURCE.add(sentinelResource.value());
            }
        }
        return bean;
    }
}
