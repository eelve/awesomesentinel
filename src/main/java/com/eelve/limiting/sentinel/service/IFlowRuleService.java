package com.eelve.limiting.sentinel.service;

import com.eelve.limiting.sentinel.entity.FlowRuleEntity;

import java.util.List;

public interface IFlowRuleService {

    List<FlowRuleEntity> allRules();

    FlowRuleEntity addRule(FlowRuleEntity flowRuleEntity);

    FlowRuleEntity updateRule(FlowRuleEntity flowRuleEntity);

    void deleteRuleById(int id);
}
