package com.gentle.cache.config;

import com.gentle.cache.open.EnableCacheManager;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.AdviceModeImportSelector;
import org.springframework.context.annotation.AutoProxyRegistrar;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Gentle
 * @date 2020/04/08 : 23:56
 */
public class CacheConfigurationSelector  extends AdviceModeImportSelector<EnableCacheManager> {

    private String[] getProxyImports() {
        List<String> result = new ArrayList<>(3);
        //自动代理注册，Spring 已经提供
        result.add(AutoProxyRegistrar.class.getName());
        //框架核心配置类
        result.add(ProxyCacheConfiguration.class.getName());
        return StringUtils.toStringArray(result);
    }


    @Override
    protected String[] selectImports(AdviceMode adviceMode) {
       //默认模式是动态代理，目前不支持 ASPECTJ
        switch (adviceMode) {
            case PROXY:
                return getProxyImports();
            case ASPECTJ:
                return null;
            default:
                return null;
        }
    }
}
