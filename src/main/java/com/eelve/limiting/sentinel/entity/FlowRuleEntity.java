package com.eelve.limiting.sentinel.entity;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhaozhilue
 */
@Data
@Entity
@Table(name = "flow_rule")
public class FlowRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="id")
    private Integer id;

    @Column(name="app")
    private String app;


    /**
     * Resource name.
     */
    @Column(name="resource")
    @NotBlank
    private String resource;

    /**
     * <p>
     * Application name that will be limited by origin.
     * The default c is {@code default}, which means allowing all origin apps.
     * </p>
     * <p>
     * For authority rules, multiple origin name can be separated with comma (',').
     * </p>
     */
    @Column(name="limit_app")
    @NotBlank
    private String limitApp;

    /**
     * The threshold type of flow control (0: thread count, 1: QPS).
     */
    @Column(name = "grade",columnDefinition="INT default 1",nullable = false)
    @NotNull
    private Integer grade = RuleConstant.FLOW_GRADE_QPS;

    /**
     * Flow control threshold count.
     */
    @Column(name = "count")
    @NotNull
    private Double count;

    /**
     * Flow control strategy based on invocation chain.
     *
     * {@link RuleConstant#STRATEGY_DIRECT} for direct flow control (by origin);
     * {@link RuleConstant#STRATEGY_RELATE} for relevant flow control (with relevant resource);
     * {@link RuleConstant#STRATEGY_CHAIN} for chain flow control (by entrance resource).
     */
    @Column(name = "strategy",columnDefinition="INT default 0",nullable = false)
    private Integer strategy = RuleConstant.STRATEGY_DIRECT;

    /**
     * Reference resource in flow control with relevant resource or context.
     */
    @Column(name = "ref_resource")
    private String refResource;

    /**
     * Rate limiter control behavior.
     * 0. default(reject directly), 1. warm up, 2. rate limiter, 3. warm up + rate limiter
     */
    @Column(name = "control_behavior",columnDefinition="INT default 0",nullable = false)
    private Integer controlBehavior = RuleConstant.CONTROL_BEHAVIOR_DEFAULT;

    @Column(name = "warm_up_period_sec")
    private Integer warmUpPeriodSec = 10;

    /**
     * Max queueing time in rate limiter behavior.
     */
    @Column(name = "max_queueing_time_ms")
    private Integer maxQueueingTimeMs = 500;

    @Column(name = "cluster_mode",columnDefinition="BOOLEAN default false",nullable = false)
    private Boolean clusterMode = false;

    public FlowRule toRule() {
        FlowRule flowRule = new FlowRule();
        flowRule.setCount(this.count);
        flowRule.setGrade(this.grade);
        flowRule.setResource(this.resource);
        flowRule.setLimitApp(this.limitApp);
        flowRule.setRefResource(this.refResource);
        flowRule.setStrategy(this.strategy);
        if (this.controlBehavior != null) {
            flowRule.setControlBehavior(controlBehavior);
        }
        if (this.warmUpPeriodSec != null) {
            flowRule.setWarmUpPeriodSec(warmUpPeriodSec);
        }
        if (this.maxQueueingTimeMs != null) {
            flowRule.setMaxQueueingTimeMs(maxQueueingTimeMs);
        }
        flowRule.setClusterMode(clusterMode);
        return flowRule;
    }

}
