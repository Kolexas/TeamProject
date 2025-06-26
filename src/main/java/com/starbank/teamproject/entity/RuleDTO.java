package com.starbank.teamproject.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.starbank.teamproject.model.DynamicRule;

import java.util.UUID;

public record RuleDTO(
        @JsonProperty("product_name") String productName,
        @JsonProperty("product_id") UUID productId,
        @JsonProperty("product_text") String text,
        @JsonProperty("rule") DynamicRule[] rule
) {
    public static RuleDTO fromRule(Rule rule) {
        return new RuleDTO(rule.getProductName(), rule.getProductId(), rule.getProductText(), rule.getRule());
    }
}