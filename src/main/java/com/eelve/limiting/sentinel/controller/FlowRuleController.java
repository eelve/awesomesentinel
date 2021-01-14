package com.eelve.limiting.sentinel.controller;

import com.eelve.limiting.sentinel.entity.FlowRuleEntity;
import com.eelve.limiting.sentinel.service.RuleServiceImpl;
import com.eelve.limiting.sentinel.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class FlowRuleController {

    @Autowired
    private RuleServiceImpl ruleService;

    @RequestMapping("/allRules")
    @ResponseBody
    public JsonResult allRules(HttpServletRequest request, HttpServletResponse response){
        return JsonResult.ok().put(ruleService.allRules());
    }

    @PostMapping("/addRule")
    @ResponseBody
    public JsonResult addRule(HttpServletRequest request, HttpServletResponse response, @RequestBody FlowRuleEntity flowRuleEntity){
        return JsonResult.ok().put(ruleService.addRule(flowRuleEntity));
    }
}
