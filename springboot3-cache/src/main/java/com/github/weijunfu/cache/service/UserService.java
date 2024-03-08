package com.github.weijunfu.cache.service;

import com.github.weijunfu.cache.entity.Author;
import com.github.weijunfu.cache.entity.User;

import java.util.List;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 15:01
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
public interface UserService {

    User getUser(String id) throws IllegalAccessException;

    List<User> getAllUser();

    void addUser(User user);

    void update(User user);

    void delete(String id);
}
