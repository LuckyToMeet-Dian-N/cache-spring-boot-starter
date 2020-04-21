package com.gentle.cache.parser;

import com.gentle.cache.annotation.PutCache;
import com.gentle.cache.annotation.RemoveCache;
import com.gentle.cache.source.CacheOperation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 注解解析器
 * @author wen
 */
public class CacheAnnotationParser implements AnnotationParser {
    @Override
    public Collection<CacheOperation> parseCacheAnnotations(Method method) {
        Annotation[] annotations = method.getAnnotations();
        return  Arrays.stream(annotations).filter(this::filterSupportAnnotations
                ).map(this::buildCacheOperation).collect(Collectors.toList());
    }

    private Boolean filterSupportAnnotations(Annotation annotation){
        String name = annotation.annotationType().getName();
        return PutCache.class.getName().equals(name)
                ||RemoveCache.class.getName().equals(name);
    }

    private CacheOperation buildCacheOperation(Annotation annotation){
        String name = annotation.annotationType().getName();
        CacheOperation operation;
        if (PutCache.class.getName().equals(name)){
            PutCache putCache = (PutCache)annotation;
            operation =  new CacheOperation.Builder().key(putCache.key()).name(putCache.prefix()).time(putCache.time()).type(0).build();
        }else {
            RemoveCache putCache = (RemoveCache)annotation;
            operation =  new CacheOperation.Builder().key(putCache.key()).name(putCache.prefix()).type(1).build();
        }
        return operation;
    }


}
