package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @ClassName SentinelResourcetHolder
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/1/30 9:45
 * @Version 1.0
 **/
@Component
public class SentinelResourcetHolder implements ApplicationContextAware {

    private static final Set<String> SENTINEL_RESOURCE = new HashSet();

    public static Set<String> getSentinelResource() {
        return SENTINEL_RESOURCE;
    }

    private static ApplicationContext applicationContext = null;

    @PostConstruct
    private void inintSentinelResourcetHolder(){
        Map<String, Object> objectMap =  applicationContext.getBeansWithAnnotation(Controller.class);
        objectMap.entrySet().forEach(o -> {
            Method[] methods = o.getValue().getClass().getDeclaredMethods();
            for (Method method : methods) {
                SentinelResource sentinelResource = AnnotationUtils.findAnnotation(method, SentinelResource.class);
                if (!Objects.isNull(sentinelResource)){
                    SENTINEL_RESOURCE.add(sentinelResource.value());
                }
            }
        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SentinelResourcetHolder.applicationContext = applicationContext;
    }

}
