package com.github.weijunfu.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class CacheApp {

    public static void main(String[] args) {
        SpringApplication.run(CacheApp.class, args);
    }

}
