package com.starbank.teamproject.controller;

import com.starbank.teamproject.model.RecommendationDTO;
import com.starbank.teamproject.service.RecommendationsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/recommendation")
public class RecommendationsController {
    private final RecommendationsService recommendationsService;

    public RecommendationsController(RecommendationsService recommendationsService) {
        this.recommendationsService = recommendationsService;
    }

    @GetMapping(value = "/{userId}")
    public ResponseEntity<List<RecommendationDTO>> getRecommendation(@PathVariable UUID userId) {
        List<RecommendationDTO> recommendedProductsForUser = recommendationsService.getRecommendedProductsForUser(userId);
        System.out.println("recommendedProductsForUser = " + recommendedProductsForUser);
        return ResponseEntity.ok(recommendedProductsForUser);
    }
}
