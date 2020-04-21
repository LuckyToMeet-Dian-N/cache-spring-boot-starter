package com.gentle.cache.pointcut;

import com.gentle.cache.annotation.PutCache;
import com.gentle.cache.annotation.RemoveCache;
import org.springframework.aop.support.StaticMethodMatcherPointcut;

import java.io.Serializable;
import java.lang.reflect.Method;

/**
 *
 * @author Gentle
 * @date 2020/04/08 : 20:00
 */
public class CacheChooseSourcePointcut extends StaticMethodMatcherPointcut implements Serializable {

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return method.isAnnotationPresent(PutCache.class)||method.isAnnotationPresent(RemoveCache.class);
    }

}
