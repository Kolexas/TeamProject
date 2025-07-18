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
    private RecommendationsRepository recommendationsRepository;
    private List<RecommendationRuleSet> recommendationRuleSets; // Inject a list of rule sets

    public RecommendationsService(RecommendationsRepository recommendationsRepository, List<RecommendationRuleSet> recommendationRuleSets) { // Inject the list
        this.recommendationsRepository = recommendationsRepository;
        this.recommendationRuleSets = recommendationRuleSets;
    }

    public List<RecommendationDTO> getClientRecommendations(UUID userId) {
        return recommendationRuleSets.stream()
                .map(ruleSet -> ruleSet.CheckRule(userId))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }
}