package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.eelve.limiting.sentinel.enums.RulesEnum;
import com.eelve.limiting.sentinel.service.RuleServiceImpl;
import com.eelve.limiting.sentinel.util.RefreshRulesUtil;
import com.eelve.limiting.sentinel.vo.SpringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

public class DataSourceInitFunc implements InitFunc {

    private static final RuleServiceImpl ruleService = SpringUtils.getBean(RuleServiceImpl.class);

    @Override
    public void init() throws Exception {
        List<FlowRule> ruleList =  ruleService.allRules().stream().map(x -> x.toRule()).collect(Collectors.toList());
        RefreshRulesUtil.refreshRule(ruleList, RulesEnum.Flow);
    }
}
