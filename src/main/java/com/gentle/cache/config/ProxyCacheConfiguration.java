package com.gentle.cache.config;

import com.gentle.cache.constant.CacheContext;
import com.gentle.cache.advisor.BeanFactoryCacheInstacneAdvisor;
import com.gentle.cache.interceptor.CacheInterceptor;
import com.gentle.cache.resolver.LocalParameterResolver;
import com.gentle.cache.resolver.ParameterResolver;
import com.gentle.cache.source.AnnotationCacheOperationSource;
import com.gentle.cache.source.CacheOperationSource;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.cache.annotation.CachingConfigurationSelector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;
import org.springframework.util.ClassUtils;

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


    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public BeanFactoryCacheInstacneAdvisor cacheAdvisor() {
        BeanFactoryCacheInstacneAdvisor advisor = new BeanFactoryCacheInstacneAdvisor();
        advisor.setAdvice(createBeanRouteInterceptor());
        if (Objects.nonNull(this.enableCaching)) {
            advisor.setOrder(this.enableCaching.<Integer>getNumber("order"));
            CacheContext.STORE_TYPE = this.enableCaching.getEnum("type");
            CacheContext.ASYN = this.enableCaching.getBoolean("asyn");
        }
        return advisor;
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheOperationSource createCacheOperationSource() {
        return new AnnotationCacheOperationSource();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public ParameterResolver createParameterResolver() {
        return new LocalParameterResolver();
    }

    @Bean
    @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
    public CacheInterceptor createBeanRouteInterceptor() {
        CacheInterceptor interceptor = new CacheInterceptor();
        interceptor.setCacheOperationSource(createCacheOperationSource());
        interceptor.setParameterResolver(createParameterResolver());
        return interceptor;
    }

}
