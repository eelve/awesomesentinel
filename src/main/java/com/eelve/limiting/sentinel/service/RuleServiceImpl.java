package com.eelve.limiting.sentinel.service;

import com.eelve.limiting.sentinel.dao.FlowRuleDao;
import com.eelve.limiting.sentinel.entity.FlowRuleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaozhilue
 */
@Service
public class RuleServiceImpl implements IRuleService{

    @Autowired
    private FlowRuleDao flowRuleDao;

    @Override
    public List<FlowRuleEntity> allRules() {
        return flowRuleDao.findAll();
    }

    @Override
    public FlowRuleEntity addRule(FlowRuleEntity flowRuleEntity) {
        return flowRuleDao.save(flowRuleEntity);
    }
}
