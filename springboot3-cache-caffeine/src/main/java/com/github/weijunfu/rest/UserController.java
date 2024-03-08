package com.github.weijunfu.rest;

import com.github.weijunfu.entity.User;
import com.github.weijunfu.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @Title  : 
 * @Author : ijunfu <ijunfu@163.com>
 * @Date   : 2024/3/8 22:29
 * @Version: 1.0
 * @Motto  : 简洁的代码是智慧的结晶 卓越的编码是对复杂性的优雅征服
 *
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/usr")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getById(@PathVariable Long id) {
        return userService.getUser(id);
    }
}
