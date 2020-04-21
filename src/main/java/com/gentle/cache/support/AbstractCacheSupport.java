package com.gentle.cache.support;

import com.gentle.cache.resolver.ParameterResolver;
import com.gentle.cache.source.CacheOperation;
import com.gentle.cache.source.CacheOperationSource;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author Gentle
 * @date 2020/04/09 : 00:03
 */
public abstract class AbstractCacheSupport extends AbstractCacheFactory implements BeanFactoryAware, InitializingBean, SmartInitializingSingleton {
    private CacheOperationSource cacheOperationSource;

    private BeanFactory beanFactory;

    private ParameterResolver parameterResolver;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() {

    }

    @Override
    public void afterSingletonsInstantiated() {

    }

    public CacheOperationSource getCacheOperationSource() {
        return cacheOperationSource;
    }

    public void setCacheOperationSource(CacheOperationSource cacheOperationSource) {
        this.cacheOperationSource = cacheOperationSource;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public ParameterResolver getParameterResolver() {
        return parameterResolver;
    }

    public void setParameterResolver(ParameterResolver parameterResolver) {
        this.parameterResolver = parameterResolver;
    }

    protected abstract Object postProcessBefore(Method method, Object[] args,Collection<CacheOperation> cacheOperations);

    protected abstract void postProcessAfter(Object value,Method method,Object[] args,Collection<CacheOperation> cacheOperations);


    protected <T> T getBean(String beanName, Class<T> expectedType) {
        if (this.beanFactory == null) {
            throw new IllegalStateException(
                    "BeanFactory must be set on cache aspect for " + expectedType.getSimpleName() + " retrieval");
        }
        return BeanFactoryAnnotationUtils.qualifiedBeanOfType(this.beanFactory, expectedType, beanName);
    }

    protected String getCacheKey(Method method,Object[] args,CacheOperation cacheOperation){
        return  parameterResolver.resolverParameter(method,args,cacheOperation);
    }
}
