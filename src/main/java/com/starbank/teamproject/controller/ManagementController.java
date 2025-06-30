package com.starbank.teamproject.controller;


import com.starbank.teamproject.configuration.CacheConfig;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final CacheConfig cacheConfig;

    public ManagementController(CacheConfig cacheConfig) {
        this.cacheConfig = cacheConfig;
    }

    @PostMapping("/clear-caches")
    public void clearCashes() {
        cacheConfig.clearAllCaches();
    }
}
