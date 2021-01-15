package com.eelve.limiting.sentinel.config;

import com.alibaba.csp.sentinel.init.InitFunc;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.circuitbreaker.CircuitBreakerStrategy;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhaozhilue
 */
public class RuleInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {

        initFlowQpsRule();

        initDegradeRule();

        initSystemProtectionRule();
    }

    /**
     * 初始化流量规则
     */
    private static void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource("allInfos");
        // Set max qps to 2
        rule1.setCount(2);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 初始化熔断规则
     */
    private static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule("allInfos")
        .setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())
        .setCount(0.7) // Threshold is 70% error ratio
        .setMinRequestAmount(100)
                .setStatIntervalMs(30000) // 30s
                .setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    /**
     * 初始化系统保护跪着
     */
    private void initSystemProtectionRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(10);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }
}
