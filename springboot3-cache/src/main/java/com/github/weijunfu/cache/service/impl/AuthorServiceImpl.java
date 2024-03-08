package com.github.weijunfu.cache.service.impl;

import com.github.weijunfu.cache.entity.Author;
import com.github.weijunfu.cache.service.AuthorService;
import com.github.weijunfu.cache.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
public class AuthorServiceImpl implements AuthorService {

    static Map<String, Author> db = new HashMap<>();

    static {
        db.put("1", new Author("1", "ijunfu"));
        db.put("2", new Author("2", "wei"));
    }

    @Override
    @Cacheable(value = "author", keyGenerator ="cacheKeyGenerator" )
    public List<Author> getAllAuthor() {
        log.info("query db");
        return db.values().stream().toList();
    }
}
