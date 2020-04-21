package com.gentle.cache.parser;

import com.gentle.cache.source.CacheOperation;
import org.springframework.lang.Nullable;


import java.lang.reflect.Method;
import java.util.Collection;

/**
 * 注解解析器
 * @author Gentle
 * @date 2020/04/08 : 19:21
 */
public interface AnnotationParser {

    @Nullable
    Collection<CacheOperation> parseCacheAnnotations(Method method);


}



