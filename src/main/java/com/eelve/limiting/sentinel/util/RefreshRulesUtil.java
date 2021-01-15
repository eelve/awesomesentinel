package com.eelve.limiting.sentinel.util;

import com.alibaba.csp.sentinel.slots.block.AbstractRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.eelve.limiting.sentinel.enums.RulesEnum;
import lombok.extern.java.Log;

import java.util.List;

/**
 * @author zhaozhilue
 */
@Log
public class RefreshRulesUtil {

    public static <T extends AbstractRule> void refreshRule(List<T> ruleList, RulesEnum rulesEnum){

        log.info("操作类型:"+rulesEnum.getCode() + ",ruleList:" + ruleList.toString());

        switch (rulesEnum){
            case Flow:
                FlowRuleManager.loadRules((List<FlowRule>) ruleList);
                break;
            case Degrade:
                DegradeRuleManager.loadRules((List<DegradeRule>)ruleList);
                break;
            case System:
                SystemRuleManager.loadRules((List<SystemRule>)ruleList);
                break;
            case Authority:
                AuthorityRuleManager.loadRules((List<AuthorityRule>)ruleList);
                break;
            default:
                log.info("无效操作");
                break;

        }
    }
}
