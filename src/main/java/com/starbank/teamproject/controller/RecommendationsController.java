package com.starbank.teamproject.controller;

import com.starbank.teamproject.service.RecommendationsService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {
    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }
}
