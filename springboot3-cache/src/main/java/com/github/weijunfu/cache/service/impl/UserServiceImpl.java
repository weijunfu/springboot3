package com.github.weijunfu.cache.service.impl;

import com.github.weijunfu.cache.entity.User;
import com.github.weijunfu.cache.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 15:02
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@Slf4j
@Service
public class UserServiceImpl implements UserService {

    static Map<String, User> db = new HashMap<>();

    static {
        db.put("1", new User("1", "ijunfu"));
        db.put("2", new User("2", "wei"));
    }

    @Override
    @Cacheable(value = "usr", key = "'user:all'")
    public List<User> getAllUser() {
        return db.values().stream().toList();
    }

    @Override
    @Cacheable(value = "usr", key = "'user:'+#id")
    public User getUser(String id) throws IllegalAccessException {

        String msg = String.format("user(id=%s) not exist!", id);
        if(!StringUtils.hasText(id)) {
            throw new IllegalAccessException(msg);
        }

        log.warn("query user name from db.");
        User user = db.get(id);

        if(Objects.isNull(user)) {
            throw new IllegalAccessException(msg);
        }

        return user;
    }

    @Override
    @CacheEvict(value = "usr", key = "'user:all'")
    public void addUser(User user) {
        if(db.containsKey(user.getId())) {
            log.error("user({}) already exist!", user.getId());
            return;
        }

        db.put(user.getId(), user);
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = "usr", key = "'user:all'")
            },
            put = {
                @CachePut(value = "usr", key = "'user:'+#user.id")
            }
    )
    public void update(User user) {
        if (db.containsKey(user.getId())) {
            log.info("update user: {}", user);
            db.put(user.getId(), user);
            return;
        }

        log.warn("user({}) not exist!", user.getId());
    }

    @Override
    @Caching(evict = {
            @CacheEvict(value = "usr", key = "'user:' + #id"),
            @CacheEvict(value = "usr", key = "'user:all'")
    })
    public void delete(String id) {
        log.warn("delete user id={}", id);
        db.remove(id);
    }
}
