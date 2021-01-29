package com.eelve.limiting.sentinel.service;

import com.eelve.limiting.sentinel.entity.DegradeRuleEntity;
import com.eelve.limiting.sentinel.entity.FlowRuleEntity;

import java.util.List;

/**
 * @ClassName IDegradeRuleService
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/1/29 20:19
 * @Version 1.0
 **/
public interface IDegradeRuleService {

    List<DegradeRuleEntity> allRules();

    DegradeRuleEntity addRule(DegradeRuleEntity degradeRuleEntity);

    DegradeRuleEntity updateRule(DegradeRuleEntity degradeRuleEntity);

    void deleteRuleById(int id);
}
