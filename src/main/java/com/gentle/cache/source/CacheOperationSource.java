package com.gentle.cache.source;



import org.springframework.lang.Nullable;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author wen
 */
public interface CacheOperationSource {

    /**
     *
     * @param method
     * @param targetClass
     * @return
     */
    Collection<CacheOperation> getCacheOperations(Method method, @Nullable Class<?> targetClass);
}