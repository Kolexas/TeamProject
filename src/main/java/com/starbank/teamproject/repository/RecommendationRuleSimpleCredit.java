package com.starbank.teamproject.repository;

import com.starbank.teamproject.model.RecommendationDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class RecommendationRuleSimpleCredit implements RecommendationRuleSet  {
    @Override
    public Optional<RecommendationDTO> CheckRule(UUID userId) {
        return Optional.empty();
    }
}
