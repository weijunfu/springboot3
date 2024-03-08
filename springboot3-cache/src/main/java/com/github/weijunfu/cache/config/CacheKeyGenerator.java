package com.github.weijunfu.cache.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 21:06
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@Component
public class CacheKeyGenerator implements KeyGenerator {
    @Override
    public Object generate(Object target, Method method, Object... params) {
        // 这里可以根据实际需求生成自定义的key，比如将方法名、参数等组合起来
        StringBuilder keyBuilder = new StringBuilder();
        keyBuilder.append(target.getClass().getCanonicalName());
        keyBuilder.append(method.getName());
        for (Object param : params) {
            if (param != null) {
                keyBuilder.append("-").append(param.toString());
            }
        }
        return keyBuilder.toString();
    }
}
