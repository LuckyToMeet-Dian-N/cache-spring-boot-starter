package com.gentle.cache.annotation;

import java.lang.annotation.*;

/**
 * @author Gentle
 * @date 2020/04/09 : 12:10
 */
@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface PutCache {
    /**
     * 存储key的具体值，例如：name，name的值为 1
     * 存储key为 {prefix}:1
     * @return key
     */
    String key();

    /**
     * key 前缀，可为空,
     * 空时默认前缀为包全限定名称+方法名称
     * 例如：com.gentle.cache.controller.CacheController.testCache
     * @return
     */
    String prefix() default "";

    long time() default -1;


}
