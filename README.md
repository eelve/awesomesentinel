# awesomesentinel

> 动态加载sentinel熔断规则实现案例。规则的持久化是利用数据库的，通过开放接口来修改规则。

> 注意：加载的规则必须满足规则要求，不然在调用api加载规则时会被过滤。


> 有COntroller层和Service层的直接第一层方法才能通过注解触发，如果是方法再调用普通方法需要勇SphO或者SphU原生写法

~~~java

    /**
     * Check whether provided flow rule is valid.
     *
     * @param rule flow rule to check
     * @return true if valid, otherwise false
     */
    public static boolean isValidRule(FlowRule rule) {
        boolean baseValid = rule != null && !StringUtil.isBlank(rule.getResource()) && rule.getCount() >= 0
            && rule.getGrade() >= 0 && rule.getStrategy() >= 0 && rule.getControlBehavior() >= 0;
        if (!baseValid) {
            return false;
        }
        // Check strategy and control (shaping) behavior.
        return checkClusterField(rule) && checkStrategyField(rule) && checkControlBehaviorField(rule);
    }

    private static boolean checkClusterField(/*@NonNull*/ FlowRule rule) {
        if (!rule.isClusterMode()) {
            return true;
        }
        ClusterFlowConfig clusterConfig = rule.getClusterConfig();
        if (clusterConfig == null) {
            return false;
        }
        if (!validClusterRuleId(clusterConfig.getFlowId())) {
            return false;
        }
        if (!isWindowConfigValid(clusterConfig.getSampleCount(), clusterConfig.getWindowIntervalMs())) {
            return false;
        }
        switch (clusterConfig.getStrategy()) {
            case ClusterRuleConstant.FLOW_CLUSTER_STRATEGY_NORMAL:
                return true;
            default:
                return false;
        }
    }

~~~

~~~java

    public static boolean isValidRule(DegradeRule rule) {
        boolean baseValid = rule != null && !StringUtil.isBlank(rule.getResource())
            && rule.getCount() >= 0 && rule.getTimeWindow() > 0;
        if (!baseValid) {
            return false;
        }
        if (rule.getMinRequestAmount() <= 0 || rule.getStatIntervalMs() <= 0) {
            return false;
        }
        switch (rule.getGrade()) {
            case RuleConstant.DEGRADE_GRADE_RT:
                return rule.getSlowRatioThreshold() >= 0 && rule.getSlowRatioThreshold() <= 1;
            case RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO:
                return rule.getCount() <= 1;
            case RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT:
                return true;
            default:
                return false;
        }
    }
~~~
