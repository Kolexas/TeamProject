package com.starbank.teamproject.repository;

import com.starbank.teamproject.model.RecommendationDTO;

import java.util.Optional;
import java.util.UUID;

public class RecommendationRuleTopSaving implements RecommendationRuleSet {
    @Override
    public Optional<RecommendationDTO> CheckRule(UUID userId) {
        return Optional.empty();
    }
}