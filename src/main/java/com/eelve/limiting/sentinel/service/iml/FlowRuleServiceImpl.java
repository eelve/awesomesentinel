package com.eelve.limiting.sentinel.service.iml;

import com.eelve.limiting.sentinel.dao.FlowRuleDao;
import com.eelve.limiting.sentinel.entity.FlowRuleEntity;
import com.eelve.limiting.sentinel.service.IFlowRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhaozhilue
 */
@Service
public class FlowRuleServiceImpl implements IFlowRuleService {

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

    @Override
    public FlowRuleEntity updateRule(FlowRuleEntity flowRuleEntity) {
        return flowRuleDao.save(flowRuleEntity);
    }

    @Override
    public void deleteRuleById(int id) {
        flowRuleDao.deleteById(id);
    }
}
