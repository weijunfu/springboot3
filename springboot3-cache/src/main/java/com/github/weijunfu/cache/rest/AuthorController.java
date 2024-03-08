package com.github.weijunfu.cache.rest;

import com.github.weijunfu.cache.entity.Author;
import com.github.weijunfu.cache.service.AuthorService;
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
@RequestMapping("/author")
@RequiredArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/")
    public List<Author> getAllAuthor() throws IllegalAccessException {
        return authorService.getAllAuthor();
    }

}
