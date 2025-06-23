package com.starbank.teamproject.entity;

import com.starbank.teamproject.model.DynamicRule;
import jakarta.persistence.*;

import java.util.UUID;


@Entity
public class Rule {

    @Id
    @GeneratedValue
    private Long id;

    private String product_name;

    private UUID product_id;

    @Column(columnDefinition="TEXT")
    private String product_text;

    private DynamicRule[] rule;

    public Rule() {

    }

    public Rule(String product_name, UUID product_id, String product_text, DynamicRule[] rule) {
        this.product_name = product_name;
        this.product_id = product_id;
        this.product_text = product_text;
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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public UUID getProduct_id() {
        return product_id;
    }

    public void setProduct_id(UUID product_id) {
        this.product_id = product_id;
    }

    public String getProduct_text() {
        return product_text;
    }

    public void setProduct_text(String product_text) {
        this.product_text = product_text;
    }
}
