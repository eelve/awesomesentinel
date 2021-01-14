package com.eelve.limiting.sentinel.dao;


import com.eelve.limiting.sentinel.entity.FlowRuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlowRuleDao extends JpaRepository<FlowRuleEntity,Integer> {
}
