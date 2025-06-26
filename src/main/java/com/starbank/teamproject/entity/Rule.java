package com.starbank.teamproject.entity;

import com.starbank.teamproject.model.DynamicRule;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
public class Rule {

    @Id
    @GeneratedValue
    private Long id;

    private String productName;

    private UUID productId;

    @Column(columnDefinition = "TEXT")
    private String productText;

    private DynamicRule[] rule;

    public Rule() {

    }

    public Rule(String productName, UUID productId, String productText, DynamicRule[] rule) {
        this.productName = productName;
        this.productId = productId;
        this.productText = productText;
        this.rule = rule;
    }

    public DynamicRule[] getRule() {
        return rule;
    }

    public void setRule(DynamicRule[] rule) {
        this.rule = rule;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }

    public String getProductText() {
        return productText;
    }

    public void setProductText(String productText) {
        this.productText = productText;
    }
}
