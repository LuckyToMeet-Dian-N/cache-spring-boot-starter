## 框架介绍：
1. 框架主要做知识点学习整理，包括 IOC，AOP，不再是学习如何使用，而是以设计者角度去设计一款框架。目前该缓存框架支持本地缓存。加入 Redis 的多级缓存正在支持。同时，也会支持缓存过期删除方式，以及简单快速新增缓存。  
2. 框架主要以注解的方式进行缓存，尚未支持使用对象去直接操作。

## 框架包分类
```
 advisor-------------顾问（切面的一种实现方式）
│annotation-------------框架支持的注解
│config-----------------配置，使用SpringBoot提供的自动配置方式
│constant-------------常量
│interceptor-------------拦截器
│open--------------------@EnableXXX 的方式
│pointcut-------------切点
│parser-------------解析器，主要解析注解信息
│source-------------获取注解信息，包括时间、缓存key名称，前缀等
│resolver-----------解析器，主要用于获取参数值
│support-------------缓存存储功能支持 
│utils---------------存放一些需要使用的工具
```
## 框架使用
下载项目，将其 install 到本地仓库。项目引入如下 jar 即可
```java
<dependency>
    <groupId>com.gentle.cache</groupId>
    <artifactId>cache-spring-boot-starter</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

## 详情查看博客
    。。。。待完善。
    





