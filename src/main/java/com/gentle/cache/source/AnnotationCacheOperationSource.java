package com.gentle.cache.source;

import com.gentle.cache.parser.CacheAnnotationParser;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Gentle
 * @date 2020/04/09 : 14:25
 */
public class AnnotationCacheOperationSource implements CacheOperationSource{

    private  Map<String,Collection<CacheOperation>> cacheCacheOperationMap = new HashMap<>(32);

    @Override
    public Collection<CacheOperation> getCacheOperations(Method method, Class<?> targetClass) {
        String key = getKey(method);
        if(cacheCacheOperationMap.containsKey(key)){
            return cacheCacheOperationMap.get(key);
        }
        Collection<CacheOperation> cacheOperations = new CacheAnnotationParser().parseCacheAnnotations(method);
        cacheCacheOperationMap.put(key,cacheOperations);
        return cacheOperations;
    }

    private String getKey(Method method){
        return  method.getDeclaringClass().getName()+"."+method.getName();
    }

}
