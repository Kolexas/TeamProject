package com.starbank.teamproject.service;

import com.starbank.teamproject.repository.RecommendationsRepository;
import org.springframework.stereotype.Service;

@Service
public class RecommendationsService {
    private final RecommendationsRepository recommendationsRepository;

    public RecommendationsService(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }
}
