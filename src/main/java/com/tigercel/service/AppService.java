package com.tigercel.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * Created by freedom on 2016/4/11.
 */
@Service
public class AppService {

    public static final String CACHE_NAME = "cache.token";

    @Cacheable(value = CACHE_NAME, key = "#token")
    public String save(String token) {

        return token;
    }

    @Cacheable(value = CACHE_NAME, key = "#token")
    public String search(String token) {

        return null;
    }
}
