package com.eelve.limiting.sentinel.dao;


import com.eelve.limiting.sentinel.entity.DegradeRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DegradeRuleDao extends JpaRepository<DegradeRuleEntity,Integer> {
}
