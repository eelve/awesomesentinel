package com.eelve.limiting.sentinel.entity;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author zhaozhilue
 */
@Data
@Entity
@Table(name = "degrade_rule")
public class DegradeRuleEntity {

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
     * The default limitApp is {@code default}, which means allowing all origin apps.
     * </p>
     * <p>
     * For authority rules, multiple origin name can be separated with comma (',').
     * </p>
     */
    @Column(name="limit_app")
    @NotBlank
    private String limitApp;

    /**
     * Circuit breaking strategy (0: average RT, 1: exception ratio, 2: exception count).
     */
    @Column(name = "grade",columnDefinition="INT default 0",nullable = false)
    @NotNull
    private Integer grade = RuleConstant.DEGRADE_GRADE_RT;

    /**
     * Threshold count.
     */
    @Column(name = "count")
    @NotNull
    private Double count;

    /**
     * Recovery timeout (in seconds) when circuit breaker opens. After the timeout, the circuit breaker will
     * transform to half-open state for trying a few requests.
     */
    @Column(name = "timeWindow")
    @NotNull
    private Integer timeWindow;

    /**
     * Minimum number of requests (in an active statistic time span) that can trigger circuit breaking.
     *
     * @since 1.7.0
     */
    @Column(name = "min_request_amount",columnDefinition="INT default 5",nullable = false)
    private Integer minRequestAmount = RuleConstant.DEGRADE_DEFAULT_MIN_REQUEST_AMOUNT;

    /**
     * The threshold of slow request ratio in RT mode.
     */
    @Column(name = "slow_ratio_threshold",columnDefinition="DOUBLE default 1000",nullable = false)
    private Double slowRatioThreshold = 1.0d;

    @Column(name = "stat_interval_ms",columnDefinition="INT default 1000",nullable = false)
    private Integer statIntervalMs = 1000;

    public DegradeRule toRule() {
        DegradeRule rule = new DegradeRule();
        rule.setResource(resource);
        rule.setLimitApp(limitApp);
        rule.setCount(count);
        rule.setTimeWindow(timeWindow);
        rule.setGrade(grade);
        if (minRequestAmount != null) {
            rule.setMinRequestAmount(minRequestAmount);
        }
        if (slowRatioThreshold != null) {
            rule.setSlowRatioThreshold(slowRatioThreshold);
        }
        if (statIntervalMs != null) {
            rule.setStatIntervalMs(statIntervalMs);
        }

        return rule;
    }
}
