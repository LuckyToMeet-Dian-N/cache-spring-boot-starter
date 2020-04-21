package com.gentle.cache.support;


import com.gentle.cache.constant.CacheContext;
import com.gentle.cache.constant.ExecuteTypeEnum;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Gentle
 * @date 2020/04/13 : 16:04
 */
public abstract class AbstractCacheFactory implements Cache {

    private static Map<String,Object> CACHE_MANAGER = new ConcurrentHashMap<>(1024);

    private static Map<String,Long> CACHE_TTL_MAP = new ConcurrentHashMap<>(1024);

    @Override
    public <T> T get(String key, Class<T> type) {

        return (T)doGet(key);
    }
    private Object doGet(String key){
        ExecuteTypeEnum storeType = CacheContext.STORE_TYPE;
        switch (storeType){
            case MEMORY:
                return getValueByMemory(key);
            case REDIS:
                return getValueByRedis(key);
            case MEMORY_AND_REDIS:
                return getValue(key);
            default:
                throw new RuntimeException("not support storyType ");
        }
    }

    private Object getValueByMemory(String key){
        return CACHE_MANAGER.get(key);
    }

    private Object getValueByRedis(String key){
        //TODO
        return CACHE_MANAGER.get(key);
    }

    private Object getValue(String key){

        Object valueByMemory = getValueByMemory(key);
        if (Objects.nonNull(valueByMemory)){
            return valueByMemory;
        }
        Object valueByRedis = getValueByRedis(key);
        if (Objects.nonNull(valueByRedis)){
            return valueByRedis;
        }
        return null;
    }

    @Override
    public void put(String key, Object value, Long ttl) {
        doPut(key,value,ttl);
    }

    private void doPut(String key,Object value ,Long  ttl){
        ExecuteTypeEnum storeType = CacheContext.STORE_TYPE;
        switch (storeType){
            case MEMORY:
                 putValueByMemory(key,value,ttl);
                 break;
            case REDIS:
                putValueByRedis(key,value,ttl);
                break;
            case MEMORY_AND_REDIS:
                putValue(key,value,ttl);
                break;
            default:
                throw new RuntimeException("not support storyType");
        }
    }
    private void putValueByMemory(String key,Object value,Long ttl){
        CACHE_MANAGER.put(key,value);
        CACHE_TTL_MAP.put(key,ttl);
    }

    private void putValueByRedis(String key,Object value,Long ttl){

    }
    private void putValue(String key,Object value,Long ttl){
        if (CacheContext.ASYN){
            CompletableFuture.runAsync(()->{
                putValueByMemory(key,value,ttl);
                putValueByRedis(key,value,ttl);
            });
        }else {
            putValueByMemory(key,value,ttl);
            putValueByRedis(key,value,ttl);
        }
    }

    @Override
    public void remove(String key) {
        doRemove(key);
    }

    private void doRemove(String key){
        ExecuteTypeEnum storeType = CacheContext.STORE_TYPE;
        switch (storeType){
            case MEMORY:
                removeValueByMemory(key);
                break;
            case REDIS:
                removeValueByRedis(key);
                break;
            case MEMORY_AND_REDIS:
                removeValue(key);
                break;
            default:
                throw new RuntimeException("not support storyType");
        }
    }
    private void removeValueByMemory(String key){
        CACHE_MANAGER.remove(key);
        CACHE_TTL_MAP.remove(key);
    }

    private void removeValueByRedis(String key){

    }
    private void removeValue(String key){
        if (CacheContext.ASYN){
            CompletableFuture.runAsync(()->{
                removeValueByMemory(key);
                removeValueByRedis(key);
            });
        }else {
            removeValueByMemory(key);
            removeValueByRedis(key);
        }
    }


}
