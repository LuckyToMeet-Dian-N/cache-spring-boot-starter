package com.gentle.cache.interceptor;

import com.gentle.cache.source.CacheOperation;
import com.gentle.cache.support.AbstractCacheSupport;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Objects;

/**
 * @author Gentle
 * @date 2020/04/08 : 17:20
 */
public class CacheInterceptor extends AbstractCacheSupport implements MethodInterceptor, Serializable {


    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Collection<CacheOperation> cacheOperations = getCacheOperationSource().getCacheOperations(invocation.getMethod(), invocation.getMethod().getClass());

        Object o = postProcessBefore(invocation.getMethod(), invocation.getArguments(), cacheOperations);
        if (Objects.nonNull(o)) {
            return o;
        }
        Object proceed = invocation.proceed();

        postProcessAfter(proceed, invocation.getMethod(), invocation.getArguments(), cacheOperations);

        return proceed;
    }


    @Override
    protected Object postProcessBefore(Method method, Object[] args, Collection<CacheOperation> cacheOperations) {

        CacheOperation cacheOperation = cacheOperations.stream().filter(operation -> operation.getType().equals(0)).findAny().orElse(null);
        if (Objects.isNull(cacheOperation)) {
            return null;
        }
        String cacheKey = getCacheKey(method, args, cacheOperation);
        return get(cacheKey, method.getReturnType());
    }

    @Override
    protected void postProcessAfter(Object value, Method method, Object[] args, Collection<CacheOperation> cacheOperations) {
        cacheOperations.forEach(cacheOperation -> {
            String cacheKey = getCacheKey(method, args, cacheOperation);
            switch (cacheOperation.getType()) {
                case 0:
                    put(cacheKey, value, cacheOperation.getTime());
                    break;
                case 1:
                    remove(cacheKey);
                    break;
                default:
                    break;
            }
        });
    }


}
