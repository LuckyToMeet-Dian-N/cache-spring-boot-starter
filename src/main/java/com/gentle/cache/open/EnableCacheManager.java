package com.gentle.cache.open;

import com.gentle.cache.config.CacheConfigurationSelector;
import com.gentle.cache.constant.ExecuteTypeEnum;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;

/**
 * @author Gentle
 * @date 2020/04/08 : 23:54
 */
@Target(ElementType.TYPE)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Import(CacheConfigurationSelector.class)
public @interface EnableCacheManager {
    /**
     * 优先级
     * @return 级别
     */
    int order() default  Ordered.LOWEST_PRECEDENCE;

    /**
     * 代理模式
     * @return
     */
    AdviceMode mode() default AdviceMode.PROXY;

    /**
     * 存储类型
     * @return
     */
    ExecuteTypeEnum type() default ExecuteTypeEnum.MEMORY;

    /**
     * 是否启用异步更新缓存
     * @return
     */
    boolean asyn() default false;

    /**
     * 代理 class，默认为动态代理，
     * 为 true 需要引入 aspectJ 包
     * 目前框架不支持
     * @return
     */
    boolean proxyTargetClass() default false;
}
