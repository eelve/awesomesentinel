package com.eelve.limiting.sentinel.controller;


import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphO;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.eelve.limiting.sentinel.config.SentinelResourceFactory;
import com.eelve.limiting.sentinel.exception.BaseException;
import com.eelve.limiting.sentinel.exception.ProgramException;
import com.eelve.limiting.sentinel.vo.JsonResult;
import lombok.extern.java.Log;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhaozhilue
 */
@RestController
@Log
public class SentinelController {

    /**
     * blockHandler = "errorReturn" 熔断 降级时触发
     * fallback = "errorReturn" 限流  发生异常时触发
     *
     * 只有COntroller层和Service层的直接第一层方法才能通过注解触发，如果是方法再调用普通方法需要勇SphO或者SphU原生写法
     */
    @RequestMapping("/sentinel")
    @ResponseBody
    //@SentinelResource(value = "sentinel",fallback = "errorReturn")
    @SentinelResource(value = "sentinel")
    public JsonResult sentinel(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num){
        log.info("SentinelResourceFactory----->" + SentinelResourceFactory.getSentinelResource().toString());
        log.info("param----->" + num);
        log.info("rule" + FlowRuleManager.getRules().toString());
        try {
            extracted(num);
            return JsonResult.ok();
        } catch (ProgramException e) {
            log.info("error");
            return JsonResult.error("error");
        }
    }

    @RequestMapping("/sentinelSphO")
    @ResponseBody
    @SentinelResource(value = "sentinelSphO")
    public JsonResult sentinelSphO(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num){
        log.info("SentinelResourceFactory----->" + SentinelResourceFactory.getSentinelResource().toString());
        log.info("param----->" + num);
        log.info("rule" + FlowRuleManager.getRules().toString());
        try {
            extractedSphO(num);
            return JsonResult.ok();
        } catch (ProgramException e) {
            log.info("error");
            return JsonResult.error("error");
        }
    }

    @RequestMapping("/sentinelSphU")
    @ResponseBody
    public JsonResult sentinelSphU(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num){
        log.info("SentinelResourceFactory----->" + SentinelResourceFactory.getSentinelResource().toString());
        log.info("param----->" + num);
        log.info("rule" + FlowRuleManager.getRules().toString());
        try {
            extractedSphU(num);
            return JsonResult.ok();
        } catch (ProgramException e) {
            log.info("error");
            return JsonResult.error("error");
        }
    }

    @SentinelResource(value = "extracted",blockHandler = "extractedHandler")
    public void extracted(Integer num) {

        if (num % 2 == 0) {
            log.info("num % 2 == 0");
            throw new BaseException("something bad with 2", 400);
        }

        if (num % 3 == 0) {
            log.info("num % 3 == 0");
            throw new BaseException("something bad with 3", 400);
        }

        if (num % 5 == 0) {
            log.info("num % 5 == 0");
            throw new ProgramException("something bad with 5", 400);
        }

        if (num % 7 == 0) {
            log.info("num % 7 == 0");
            int res = 1 / 0;
        }
    }

    private void extractedHandler(Integer num,BlockException blockException) {
        log.info("something bad with blockException");
        throw new ProgramException("something bad with blockException", 400);
    }

    private void extractedSphO(Integer num) {
        if (SphO.entry("extractedSphO")){
            try {
                if (num % 2 == 0) {
                    log.info("num % 2 == 0");
                    throw new BaseException("something bad with 2", 400);
                }

                if (num % 3 == 0) {
                    log.info("num % 3 == 0");
                    throw new BaseException("something bad with 3", 400);
                }

                if (num % 5 == 0) {
                    log.info("num % 5 == 0");
                    throw new ProgramException("something bad with 5", 400);
                }

                if (num % 7 == 0) {
                    log.info("num % 7 == 0");
                    int res = 1 / 0;
                }
            }finally {
                SphO.exit();
            }
        }else {
            log.info("something bad with blockException");
            throw new ProgramException("something bad with blockException", 400);
        }
    }

    private void extractedSphU(Integer num) {
        try (Entry entry = SphU.entry("extractedSphU")) {
            if (num % 2 == 0) {
                log.info("num % 2 == 0");
                throw new BaseException("something bad with 2", 400);
            }

            if (num % 3 == 0) {
                log.info("num % 3 == 0");
                throw new BaseException("something bad with 3", 400);
            }

            if (num % 5 == 0) {
                log.info("num % 5 == 0");
                throw new ProgramException("something bad with 5", 400);
            }

            if (num % 7 == 0) {
                log.info("num % 7 == 0");
                int res = 1 / 0;
            }
        } catch (BlockException ex) {
            log.info("something bad with blockException");
            throw new ProgramException("something bad with blockException", 400);
        }
    }
    /**
     * 限流，参数需要和方法保持一致
     * @param request
     * @param response
     * @param num
     * @return
     * @throws BlockException
     */
    public JsonResult errorReturn(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num) throws BlockException {
        return JsonResult.error("error 限流" + num );
    }

    /**
     * 熔断，参数需要和方法保持一直，并且需要添加BlockException异常
     * @param request
     * @param response
     * @param num
     * @param b
     * @return
     * @throws BlockException
     */
    public JsonResult errorReturn(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer num,BlockException b) throws BlockException {
        return JsonResult.error("error 熔断" + num );
    }

}
