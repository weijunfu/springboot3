package com.github.weijunfu.cache.rest;

import com.github.weijunfu.cache.entity.User;
import com.github.weijunfu.cache.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 16:32
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@RequestMapping("/usr")
@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/")
    public List<User> getAllUser() throws IllegalAccessException {
        return userService.getAllUser();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) throws IllegalAccessException {
        return userService.getUser(id);
    }

    @PostMapping("/{id}/{name}")
    public List<User> addUser(@PathVariable String id, @PathVariable String name) throws IllegalAccessException {
        User user = new User();
        user.setId(id);
        user.setName(name);
        userService.addUser(user);
        return userService.getAllUser();
    }

    @PutMapping("/{id}/{name}")
    public String updateUser(@PathVariable String id, @PathVariable String name) {
        User user = new User();
        user.setId(id);
        user.setName(name);
        userService.update(user);
        return "ok";
    }

    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id) {
        userService.delete(id);
        return "ok";
    }
}
