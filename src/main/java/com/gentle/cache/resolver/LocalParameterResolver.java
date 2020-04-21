package com.gentle.cache.resolver;

import com.gentle.cache.source.CacheOperation;
import com.gentle.cache.utils.ConversionUtils;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 参数解析实现
 *
 * @author Gentle
 * @date 2020/04/13 : 20:26
 */
public class LocalParameterResolver implements ParameterResolver {
    Logger logger = Logger.getLogger(this.getClass().getName());

    private ParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();

    @Override
    public String resolverParameter(Method method, Object[] args, CacheOperation cacheOperation) {
        if (StringUtils.isEmpty(cacheOperation.getName())) {
            return getPreFix(method) + resolverParameter(method, args, cacheOperation.getKey());
        } else {
            return cacheOperation.getName() + ":" + resolverParameter(method, args, cacheOperation.getKey());
        }
    }

    private String getPreFix(Method method) {
        return method.getDeclaringClass().getName() + "." + method.getName() + ":";
    }

    private String resolverParameter(Method method, Object[] args, String key) {
        StringBuilder conversionValue = new StringBuilder();
        if (!StringUtils.isEmpty(key)) {
            conversionValue.append(getKeyValue2(method,key, args));
        } else {
            conversionValue.append(getKeyValue(method, args));
        }
        return conversionValue.toString();
    }

    private Map<String, Map<Class<?>, Object>> getParameterNameAndTypeAndObjectMap(Method method, Object[] args) {
        Map<String, Map<Class<?>, Object>> classMap = new HashMap<>();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        assert parameterNames != null;
        Class<?>[] parameterTypes = method.getParameterTypes();
        Map<Class<?>, Object> tmpObjectMap = null;
        for (int i = 0; i < parameterTypes.length; i++) {
            tmpObjectMap = new HashMap<>();
            tmpObjectMap.put(parameterTypes[i], args[i]);
            classMap.put(parameterNames[i], tmpObjectMap);
        }
        return classMap;
    }

    private String getKeyValue(Method method, Object[] args) {
        StringBuilder valueString = new StringBuilder();
        Class<?>[] parameterTypes = method.getParameterTypes();
        String[] parameterNames = parameterNameDiscoverer.getParameterNames(method);
        assert parameterNames != null;
        for (int i = 0; i < parameterTypes.length; i++) {
            if (ConversionUtils.isBaseDefaultValue(parameterTypes[i])) {
                valueString.append(args[i]).append("_");
            } else {
                valueString.append(parameterNames[i]).append("_");
            }
        }
        return valueString.toString();
    }

    private String getKeyValue2(Method method, String key, Object[] args)   {
        StringBuilder valueString = new StringBuilder();
        Map<String, Map<Class<?>, Object>> parameterNameAndTypeMap = getParameterNameAndTypeAndObjectMap(method, args);
        String[] split = key.split("_");
        for (int i = 0; i < split.length; i++) {
            if (split[i].contains(".")) {
                try {
                    String parameterObjectName = split[i].substring(split[i].indexOf(".") + 1);
                    if (parameterNameAndTypeMap.containsKey(parameterObjectName)){
                        valueString.append(getObjectKey(args[i],parameterObjectName)).append("_");
                    }
                    throw  new IllegalArgumentException("parameter Object "+split[i] + "  is not found");
                }catch (Exception ex){
                   ex.printStackTrace();
                }
            } else if (parameterNameAndTypeMap.containsKey(split[i])) {
                valueString.append(args[i]).append("_");
            } else {
                valueString.append(split[i]).append("_");
            }
        }
        return valueString.toString();
    }

    private Object getObjectKey(Object object, String key) throws Exception {
        //判断key是否为空
        if (StringUtils.isEmpty(key)) {
            return object;
        }
        //拿到user.xxx  例如：key是user.user.id  递归取到最后的id。并返回数值
        int doIndex = key.indexOf(".");
        if (doIndex > 0) {
            String propertyName = key.substring(0, doIndex);
            //截取
            key = key.substring(doIndex + 1);
            Object obj = getProperty(object, getMethod(propertyName));
            return getObjectKey(obj, key);
        }
        return getProperty(object, getMethod(key));
    }

    private Object getProperty(Object object, String methodName) throws Exception {
        return object.getClass().getMethod(methodName).invoke(object);
    }

    private String getMethod(String key) {
        return "get" + Character.toUpperCase(key.charAt(0)) + key.substring(1);
    }

    private boolean isNotInstance(Class clazz) {
        return (clazz.isInterface() || Modifier.isAbstract(clazz.getModifiers()));
    }

//    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
//        TestCache testCache = new TestCache();
//        Method test444 = testCache.getClass().getMethod("test444", Integer.class, TestCache.class);
//
//        CacheOperation cacheOperation = new CacheOperation.Builder().key("id_user").type(0).build();
//
//        Object[] objects = new Object[]{444,new TestCache()};
//        ParameterResolver resolver =   new LocalParameterResolver();
//        String s = resolver.resolverParameter(test444, objects, cacheOperation);
//        System.out.println(s);
//    }
}
