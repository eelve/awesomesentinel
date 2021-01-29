package com.eelve.limiting.sentinel.controller;

import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.eelve.limiting.sentinel.entity.FlowRuleEntity;
import com.eelve.limiting.sentinel.enums.RulesEnum;
import com.eelve.limiting.sentinel.service.iml.FlowRuleServiceImpl;
import com.eelve.limiting.sentinel.util.RefreshRulesUtil;
import com.eelve.limiting.sentinel.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/eelve/flow-rule")
public class FlowRuleController {

    @Autowired
    private FlowRuleServiceImpl flowRuleService;

    @GetMapping("/rules")
    @ResponseBody
    public JsonResult allRules(HttpServletRequest request, HttpServletResponse response){
        List<FlowRule> ruleList =  flowRuleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Flow);
        return JsonResult.ok().put(flowRuleService.allRules());
    }

    @PostMapping("/rules")
    @ResponseBody
    public JsonResult addRule(HttpServletRequest request, HttpServletResponse response, @RequestBody FlowRuleEntity flowRuleEntity){
        /**
         * 先添加，然后再查询出来批量更新
         */
        flowRuleService.addRule(flowRuleEntity);
        List<FlowRule> ruleList =  flowRuleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Flow);
        return JsonResult.ok().put(flowRuleEntity);
    }

    @PutMapping("/rules")
    @ResponseBody
    public JsonResult updateRule(HttpServletRequest request, HttpServletResponse response, @RequestBody FlowRuleEntity flowRuleEntity){
        /**
         * 先添加，然后再查询出来批量更新
         */
        flowRuleService.addRule(flowRuleEntity);
        List<FlowRule> ruleList =  flowRuleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Flow);
        return JsonResult.ok().put(flowRuleEntity);
    }

    @DeleteMapping("/rules/{id}")
    @ResponseBody
    public JsonResult deleteRule(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "id") Integer id){
        /**
         * 先添加，然后再查询出来批量更新
         */
        flowRuleService.deleteRuleById(id);
        List<FlowRule> ruleList =  flowRuleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Flow);
        return JsonResult.ok();
    }
}
