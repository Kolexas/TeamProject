package com.starbank.teamproject.controller;

import com.starbank.teamproject.entity.Rule;
import com.starbank.teamproject.entity.RuleDTO;
import com.starbank.teamproject.model.DynamicRule;
import com.starbank.teamproject.service.RuleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.UUID;

@RestController
@RequestMapping("/rules_api")
public class RuleApiController {

    private RuleService ruleService;

    public RuleApiController(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping("/add")
    public Rule addRule(@RequestBody Rule rule) {
        return ruleService.addRule(rule);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteRule(@PathVariable Long id) {
        ruleService.deleteRule(id);
        return ResponseEntity.ok("204 No content");
    }

    @GetMapping("/getAll")
    public Collection<RuleDTO> getAllRules() {
        return ruleService.getAllRules();
    }

    @GetMapping("/getAllRulesById/{userId}")
    public Collection<DynamicRule> getAllRulesByUserId(@PathVariable UUID userId) {
        return ruleService.getAllRulesCheckByUserId(userId);
    }
}
