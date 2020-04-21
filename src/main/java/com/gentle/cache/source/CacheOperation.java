package com.gentle.cache.source;

/**
 * @author Gentle
 * @date 2020/04/08 : 20:37
 */
public class CacheOperation {

    private String key;

    private String name;

    private Long time;
    /**
     * 0-add ,1-delete
     */
    private Integer type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public CacheOperation(Builder builder){
        this.time = builder.time;
        this.name = builder.name;
        this.key = builder.key;
        this.type = builder.type;
    }

    public static class Builder {
        private String key;

        private String name;

        private Long time;

        private Integer type;

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder time(Long time) {
            this.time = time;
            return this;
        }
        public Builder type(Integer type) {
            this.type = type;
            return this;
        }
        public CacheOperation build() {
            return new CacheOperation(this);
        }
    }


}


