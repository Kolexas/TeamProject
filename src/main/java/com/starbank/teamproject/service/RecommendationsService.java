package com.starbank.teamproject.service;

import com.starbank.teamproject.model.RecommendationDTO;
import com.starbank.teamproject.repository.RecommendationRuleSet;
import com.starbank.teamproject.repository.RecommendationsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RecommendationsService {
    private  RecommendationsRepository recommendationsRepository;
    private  List<RecommendationRuleSet> recommendationRuleSets;

    public RecommendationsService(RecommendationsRepository recommendationsRepository, List<RecommendationRuleSet> recommendationRuleSets) {
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationRuleSets = recommendationRuleSets;
    }

    public List<RecommendationDTO> getRecommendedProductsForUser(UUID userId) {
        return recommendationRuleSets.stream()
                .map(ruleSet -> ruleSet.CheckRule(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}
