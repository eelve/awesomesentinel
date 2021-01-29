package com.eelve.limiting.sentinel.service.iml;

import com.eelve.limiting.sentinel.dao.DegradeRuleDao;
import com.eelve.limiting.sentinel.entity.DegradeRuleEntity;
import com.eelve.limiting.sentinel.service.IDegradeRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName DegradeRuleServiceImpl
 * @Description TODO
 * @Author zhao.zhilue
 * @Date 2021/1/29 20:20
 * @Version 1.0
 **/
@Service
public class DegradeRuleServiceImpl implements IDegradeRuleService {

    @Autowired
    private DegradeRuleDao degradeRuleDao;

    @Override
    public List<DegradeRuleEntity> allRules() {
        return degradeRuleDao.findAll();
    }

    @Override
    public DegradeRuleEntity addRule(DegradeRuleEntity degradeRuleEntity) {
        return degradeRuleDao.save(degradeRuleEntity);
    }

    @Override
    public DegradeRuleEntity updateRule(DegradeRuleEntity degradeRuleEntity) {
        return degradeRuleDao.save(degradeRuleEntity);
    }

    @Override
    public void deleteRuleById(int id) {
        degradeRuleDao.deleteById(id);
    }
}
