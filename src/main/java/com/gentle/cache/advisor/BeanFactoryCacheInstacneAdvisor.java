package com.gentle.cache.advisor;

import com.gentle.cache.pointcut.CacheChooseSourcePointcut;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;

/**
 * @author Gentle
 * @date 2020/04/08 : 23:59
 */
public class BeanFactoryCacheInstacneAdvisor extends AbstractBeanFactoryPointcutAdvisor {

    private CacheChooseSourcePointcut pointcut = new CacheChooseSourcePointcut();

    @Override
    public Pointcut getPointcut() {
        return pointcut;
    }

}
