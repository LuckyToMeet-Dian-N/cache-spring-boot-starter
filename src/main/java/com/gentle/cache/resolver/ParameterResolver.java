package com.gentle.cache.resolver;

import com.gentle.cache.source.CacheOperation;

import java.lang.reflect.Method;

/**
 * 参数解析器
 * @author Gentle
 * @date 2020/04/13 : 20:24
 */
public interface ParameterResolver {

    String resolverParameter(Method method, Object[] args,CacheOperation cacheOperation);


}
