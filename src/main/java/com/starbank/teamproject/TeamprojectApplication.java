package com.starbank.teamproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class TeamprojectApplication {

    public static void main(String[] args) {
        SpringApplication.run(TeamprojectApplication.class, args);
    }
    public enum ProductType {
        DEBIT,
        CREDIT,
        INVEST,
        SAVING;
    }
}
