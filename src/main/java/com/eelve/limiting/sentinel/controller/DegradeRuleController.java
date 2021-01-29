package com.eelve.limiting.sentinel.controller;

import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.eelve.limiting.sentinel.entity.DegradeRuleEntity;
import com.eelve.limiting.sentinel.enums.RulesEnum;
import com.eelve.limiting.sentinel.service.iml.DegradeRuleServiceImpl;
import com.eelve.limiting.sentinel.util.RefreshRulesUtil;
import com.eelve.limiting.sentinel.vo.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName DegradeRuleController
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/1/29 20:18
 * @Version 1.0
 **/
@RestController
@RequestMapping("/api/eelve/degrade-rule")
public class DegradeRuleController {

    @Autowired
    private DegradeRuleServiceImpl degradeRuleService;

    @GetMapping("/rules")
    @ResponseBody
    public JsonResult allRules(HttpServletRequest request, HttpServletResponse response){
        List<DegradeRule> ruleList =  degradeRuleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Degrade);
        return JsonResult.ok().put(degradeRuleService.allRules());
    }

    @PostMapping("/rules")
    @ResponseBody
    public JsonResult addRule(HttpServletRequest request, HttpServletResponse response, @RequestBody DegradeRuleEntity degradeRuleEntity){
        /**
         * 先添加，然后再查询出来批量更新
         */
        degradeRuleService.addRule(degradeRuleEntity);
        List<DegradeRule> ruleList =  degradeRuleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Degrade);
        return JsonResult.ok().put(degradeRuleEntity);
    }

    @PutMapping("/rules")
    @ResponseBody
    public JsonResult updateRule(HttpServletRequest request, HttpServletResponse response, @RequestBody DegradeRuleEntity degradeRuleEntity){
        /**
         * 先添加，然后再查询出来批量更新
         */
        degradeRuleService.addRule(degradeRuleEntity);
        List<DegradeRule> ruleList =  degradeRuleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Degrade);
        return JsonResult.ok().put(degradeRuleEntity);
    }

    @DeleteMapping("/rules/{id}")
    @ResponseBody
    public JsonResult deleteRule(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "id") Integer id){
        /**
         * 先添加，然后再查询出来批量更新
         */
        degradeRuleService.deleteRuleById(id);
        List<DegradeRule> ruleList =  degradeRuleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Degrade);
        return JsonResult.ok();
    }
}
