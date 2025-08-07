package com.starbank.teamproject.controller;


import com.starbank.teamproject.configuration.CacheConfig;
import org.springframework.boot.info.BuildProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/management")
public class ManagementController {
    private final CacheConfig cacheConfig;
    private final BuildProperties buildProperties;

    public ManagementController(CacheConfig cacheConfig, BuildProperties buildProperties) {
        this.cacheConfig = cacheConfig;
        this.buildProperties = buildProperties;
    }

    @PostMapping("/clear-caches")
    public void clearCashes() {
        cacheConfig.clearAllCaches();
    }

    @GetMapping("/info")
    public String getServiceInfo() {
        return "name = " + buildProperties.getName() + ", version = " + buildProperties.getVersion();
    }
}
