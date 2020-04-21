package com.gentle.cache.support;

import org.springframework.lang.Nullable;

/**
 * @author Gentle
 * @date 2020/04/13 : 16:04
 */
public interface Cache {

    <T> T get(String key, @Nullable Class<T> type);

    void put(String key, Object object,Long ttl);

    void remove(String key);

}
