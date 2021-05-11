package com.eelve.limiting.sentinel.annotation;

import com.alibaba.csp.sentinel.EntryType;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

/**
 * @author zhao.zhilue
 * @Description: 自定义注解
 * @date 2021/5/1120:14
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@SentinelResource
public @interface MySentinelResource {
    /**
     * @return name of the Sentinel resource
     */
    @AliasFor(annotation = SentinelResource.class)
    String value() default "";

    /**
     * @return the entry type (inbound or outbound), outbound by default
     */
    @AliasFor(annotation = SentinelResource.class)
    EntryType entryType() default EntryType.OUT;

    /**
     * @return the classification (type) of the resource
     * @since 1.7.0
     */
    @AliasFor(annotation = SentinelResource.class)
    int resourceType() default 0;

    /**
     * @return name of the block exception function, empty by default
     */
    @AliasFor(annotation = SentinelResource.class)
    String blockHandler() default "";

    /**
     * The {@code blockHandler} is located in the same class with the original method by default.
     * However, if some methods share the same signature and intend to set the same block handler,
     * then users can set the class where the block handler exists. Note that the block handler method
     * must be static.
     *
     * @return the class where the block handler exists, should not provide more than one classes
     */
    @AliasFor(annotation = SentinelResource.class)
    Class<?>[] blockHandlerClass() default {};

    /**
     * @return name of the fallback function, empty by default
     */
    @AliasFor(annotation = SentinelResource.class)
    String fallback() default "";

    /**
     * The {@code defaultFallback} is used as the default universal fallback method.
     * It should not accept any parameters, and the return type should be compatible
     * with the original method.
     *
     * @return name of the default fallback method, empty by default
     * @since 1.6.0
     */
    @AliasFor(annotation = SentinelResource.class)
    String defaultFallback() default "";

    /**
     * The {@code fallback} is located in the same class with the original method by default.
     * However, if some methods share the same signature and intend to set the same fallback,
     * then users can set the class where the fallback function exists. Note that the shared fallback method
     * must be static.
     *
     * @return the class where the fallback method is located (only single class)
     * @since 1.6.0
     */
    @AliasFor(annotation = SentinelResource.class)
    Class<?>[] fallbackClass() default {};

    /**
     * @return the list of exception classes to trace, {@link Throwable} by default
     * @since 1.5.1
     */
    @AliasFor(annotation = SentinelResource.class)
    Class<? extends Throwable>[] exceptionsToTrace() default {Throwable.class};

    /**
     * Indicates the exceptions to be ignored. Note that {@code exceptionsToTrace} should
     * not appear with {@code exceptionsToIgnore} at the same time, or {@code exceptionsToIgnore}
     * will be of higher precedence.
     *
     * @return the list of exception classes to ignore, empty by default
     * @since 1.6.0
     */
    @AliasFor(annotation = SentinelResource.class)
    Class<? extends Throwable>[] exceptionsToIgnore() default {};
}
