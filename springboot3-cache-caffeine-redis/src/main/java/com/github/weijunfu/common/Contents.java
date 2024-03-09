package com.github.weijunfu.common;

import java.time.Duration;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/9 9:47
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
public class Contents {


    public static final String CACHE_LOCAL_NAME = "My_Local_Cache";

    public static final String CACHE_REDIS_NAME = "My_Redis_Cache";

    public static final String CACHE_REDIS_PREFIX = "RC:";

    public static final Duration CACHE_EXPIRE_DURATION = Duration.ofMinutes(1);

    private Contents(){}

}
