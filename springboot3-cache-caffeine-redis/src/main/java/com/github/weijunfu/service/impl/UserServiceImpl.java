package com.github.weijunfu.service.impl;

import com.github.weijunfu.common.Contents;
import com.github.weijunfu.entity.User;
import com.github.weijunfu.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 22:21
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    private static Map<Long, User> db = new HashMap<>();

    static {
        db.put(1L, new User(1L, "ijunfu"));
    }

    @Override
    @Cacheable(value= {
            Contents.CACHE_LOCAL_NAME,
            Contents.CACHE_REDIS_NAME
    }, key = "'user:'+#id")
    public User getUser(Long id) {
        log.info("query from db");

        if(db.containsKey(id)) {
            return db.get(id);
        }

        log.error("user(id={}) not exists", id);
        throw new RuntimeException("user not exists");
    }
}
