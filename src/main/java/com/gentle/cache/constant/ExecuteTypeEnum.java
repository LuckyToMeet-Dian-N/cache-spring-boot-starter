package com.gentle.cache.constant;

import java.util.Arrays;

/**
 * @author Gentle
 * @date 2020/04/09 : 23:39
 */
public enum ExecuteTypeEnum {
    /**
     * 执行模式
     */
    MEMORY("memory","本地内存"),
    REDIS("redis","Redis缓存"),
    MEMORY_AND_REDIS("memory_redis","多级缓存（本地内存+Redis缓存）")
    ;

    private String code;

    private String desc;

     ExecuteTypeEnum(String code,String desc){
        this.code=code;
        this.desc= desc;
    }

    public static ExecuteTypeEnum getExecuteTypeType(String code){
        return Arrays.stream(ExecuteTypeEnum.values())
                .filter(executeTypeEnum -> executeTypeEnum.getCode().equals(code))
                .findAny().orElseThrow(()->new IllegalArgumentException(code+" is not exist"));
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }}
