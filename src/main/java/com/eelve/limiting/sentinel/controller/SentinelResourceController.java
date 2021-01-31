package com.eelve.limiting.sentinel.controller;

import com.eelve.limiting.sentinel.config.SentinelResourceFactory;
import com.eelve.limiting.sentinel.config.SentinelResourcetHolder;
import com.eelve.limiting.sentinel.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName lerSentinelResourceControl
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/1/31 12:31
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/eelve/sentinel/resource")
public class SentinelResourceController {

    @Autowired
    private SentinelResourceFactory sentinelResourceFactory;

    @GetMapping("/v1")
    public JsonResult getAllSentinelResourceV1(){

        return JsonResult.ok().put(SentinelResourceFactory.getSentinelResource());
    }

    @GetMapping("/v2")
    public JsonResult getAllSentinelResourceV2(){

        return JsonResult.ok().put(SentinelResourcetHolder.getSentinelResource());
    }
}
