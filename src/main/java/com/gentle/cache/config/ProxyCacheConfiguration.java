package com.gentle.cache.config;

import com.gentle.cache.advisor.BeanFactoryCacheInstanceAdvisor;
import com.gentle.cache.constant.CacheContext;
import com.gentle.cache.interceptor.CacheInterceptor;
import com.gentle.cache.resolver.LocalParameterResolver;
import com.gentle.cache.resolver.ParameterResolver;
import com.gentle.cache.source.AnnotationCacheOperationSource;
import com.gentle.cache.source.CacheOperationSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

import java.util.Objects;

/**
 * beanRoute代理
 *
 * @author Gentle
 * @date 2020/04/08 : 18:26
 */
@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
@Configuration
public class ProxyCacheConfiguration extends AbstractCacheConfiguration {
    /**
     *  通知，AOP 实现方式之一。
     *  需要放入拦截器等信息
     * @return
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryCacheInstanceAdvisor cacheAdvisor() {
        BeanFactoryCacheInstanceAdvisor advisor = new BeanFactoryCacheInstanceAdvisor();
        //放入拦截器
        advisor.setAdvice(createBeanRouteInterceptor());
        //获取 EnableCacheManager 中配置的全局信息
        //此处设计不好，待改善。CacheContext 对外暴露了
        if (Objects.nonNull(this.enableCaching)) {
            advisor.setOrder(this.enableCaching.<Integer>getNumber("order"));
            CacheContext.STORE_TYPE = this.enableCaching.getEnum("type");
            CacheContext.ASYN = this.enableCaching.getBoolean("asyn");
        }
        return advisor;
    }
    /**
     * 注解解析器，解析注解中配置的key、prefix、time 等信息
     * @return CacheOperationSource
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheOperationSource createCacheOperationSource() {
        return new AnnotationCacheOperationSource();
    }

    /**
     * 参数解析器，根据用户提供的key，解析得到具体传入值
     * 将值拼装成缓存 key
     * @return ParameterResolver
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ParameterResolver createParameterResolver() {
        return new LocalParameterResolver();
    }

    /**
     * 拦截器，拦截被自定义注解标记的方法
     * @return  CacheInterceptor
     */
    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor createBeanRouteInterceptor() {
        CacheInterceptor interceptor = new CacheInterceptor();
        interceptor.setCacheOperationSource(createCacheOperationSource());
        interceptor.setParameterResolver(createParameterResolver());
        return interceptor;
    }

}
