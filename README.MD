# 支持注解和动态规则的实现

## 1.依赖

~~~pom
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-core</artifactId>
    <version>1.8.0</version>
</dependency>
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-annotation-aspectj</artifactId>
    <version>1.8.0</version>
</dependency>
<dependency>
    <groupId>com.alibaba.csp</groupId>
    <artifactId>sentinel-datasource-extension</artifactId>
    <version>1.8.0</version>
</dependency>
~~~

## 2.配置支持注解

~~~java
@Configuration
public class SentinelAspectConfiguration {

    @Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }
}
~~~

## 3.动态加载规则

~~~java
public class RuleInitFunc implements InitFunc {
    @Override
    public void init() throws Exception {

        initFlowQpsRule();

        initDegradeRule();

        initSystemProtectionRule();
    }

    /**
     * 初始化流量规则
     */
    private static void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource("allInfos");
        // Set max qps to 2
        rule1.setCount(0);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule1.setLimitApp("default");
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 初始化熔断规则
     */
    private static void initDegradeRule() {
        List<DegradeRule> rules = new ArrayList<>();
        DegradeRule rule = new DegradeRule("allInfos")
        .setGrade(CircuitBreakerStrategy.ERROR_RATIO.getType())
        .setCount(0.7) // Threshold is 70% error ratio
        .setMinRequestAmount(100)
                .setStatIntervalMs(30000) // 30s
                .setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    /**
     * 初始化系统保护跪着
     */
    private void initSystemProtectionRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(10);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }
}
~~~

核心代码是使用api`RuleManager.loadRules(rules);`

然后在`META-INF/services`中添加`com.alibaba.csp.sentinel.init.InitFunc`配置刚刚实现InitFunc接口的全类名反写`com.eelve.limiting.sentinel.config.RuleInitFunc`。
