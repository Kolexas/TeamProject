package com.starbank.teamproject.repository;

import com.starbank.teamproject.model.RecommendationDTO;

import java.util.Optional;
import java.util.UUID;

public interface RecommendationRuleSet {
    Optional<RecommendationDTO> CheckRule(UUID userId);
}
