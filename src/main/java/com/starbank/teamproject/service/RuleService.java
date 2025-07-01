package com.starbank.teamproject.service;

import com.starbank.teamproject.entity.Rule;
import com.starbank.teamproject.entity.RuleDTO;
import com.starbank.teamproject.entity.Statistics;
import com.starbank.teamproject.model.DynamicRule;
import com.starbank.teamproject.repository.RecommendationsRepository;
import com.starbank.teamproject.repository.RuleRepository;
import com.starbank.teamproject.repository.StatisticsRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RuleService {

    private final RuleRepository ruleRepository;
    private final RecommendationsRepository recommendationsRepository;

    private final StatisticsRepository statisticsRepository;
    public static final String USER_OF = "USER_OF";
    public static final String TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW = "TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW";
    public static final String TRANSACTION_SUM_COMPARE = "TRANSACTION_SUM_COMPARE";

    public RuleService(RuleRepository ruleRepository, RecommendationsRepository recommendationsRepository, StatisticsRepository statisticsRepository) {
        this.ruleRepository = ruleRepository;
        this.recommendationsRepository = recommendationsRepository;
        this.statisticsRepository = statisticsRepository;
    }

    public Rule addRule(Rule rule) {
        return ruleRepository.save(rule); // Вернем сохраненное правило, а не null
    }

    public void deleteRule(Long id) {
        ruleRepository.deleteById(id);
    }

    public Collection<RuleDTO> getAllRules() {
        List<Rule> rules = ruleRepository.findAll();
        return rules.stream()
                .map(RuleDTO::fromRule)  // Преобразуем каждый Rule в RuleDTO
                .collect(Collectors.toList());
    }

    // Проверяет правила относительно конкретного пользователя и возвращает динамические правила, соответствующие данному пользователю
    @Cacheable("users")
    public Collection<DynamicRule> getAllRulesCheckByUserId(UUID uuid) {
        List<Rule> rules = ruleRepository.findAll();
        Collection<DynamicRule> result = new ArrayList<>();

        boolean checkRule1 = false;
        boolean checkRule2 = false;
        boolean checkRule3 = false;


        for (Rule rule : rules) {
            DynamicRule[] dynamicRulesArray = rule.getRule();


            for (DynamicRule dynamicRule : dynamicRulesArray) {
                if (processUserOfRule(dynamicRule, uuid)) {
                    result.add(dynamicRule);
                    checkRule1 = true;
                }
                if (processDepositWithdrawComparison(dynamicRule, uuid)) {
                    result.add(dynamicRule);
                    checkRule2 = true;
                }
                if (processTransactionSumCompare(dynamicRule, uuid)) {
                    result.add(dynamicRule);
                    checkRule3 = true;
                }
            }

            if (checkRule1 && checkRule2 && checkRule3) {
                incrementExecutionCounter(rule);
                return result;
            }
        }
        return null;
    }

    public Collection<Statistics> getStats() {
        List<Statistics> all = statisticsRepository.findAll();
        return all;
    }

    public void incrementExecutionCounter(Rule rule) {
        Optional<Statistics> statsOptional = statisticsRepository.findById(rule.getId());

        if (statsOptional.isPresent()) {
            Statistics stats = statsOptional.get();
            stats.setCount(stats.getCount() + 1);
            statisticsRepository.save(stats);
        } else {
            Statistics newStats = new Statistics();
            newStats.setRule(rule);
            newStats.setCount(1L);
            statisticsRepository.save(newStats);

        }
    }

    // Обработчик правила USER_OF
    private boolean processUserOfRule(DynamicRule dynamicRule, UUID uuid) {
        if (!dynamicRule.getQuery().equals(USER_OF)) return false;

        String argument = dynamicRule.getArguments()[0]; // Тип продукта
        boolean exists = dynamicRule.isNegate()
                ? recommendationsRepository.CheckProductNONExistencesByType(uuid, argument)
                : recommendationsRepository.CheckProductExistencesByType(uuid, argument);

        return exists;
    }

    // Обработчик правила TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW
    private boolean processDepositWithdrawComparison(DynamicRule dynamicRule, UUID uuid) {
        if (!dynamicRule.getQuery().equals(TRANSACTION_SUM_COMPARE_DEPOSIT_WITHDRAW)) return false;

        String productType = dynamicRule.getArguments()[0];
        int deposit = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(uuid, "DEPOSIT", productType);
        int withdraw = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(uuid, "WITHDRAW", productType);

        boolean comparisonResult = dynamicRule.isNegate()
                ? deposit < withdraw
                : deposit > withdraw;

        return comparisonResult;
    }

    // Обработчик правила TRANSACTION_SUM_COMPARE
    private boolean processTransactionSumCompare(DynamicRule dynamicRule, UUID uuid) {
        if (!dynamicRule.getQuery().equals(TRANSACTION_SUM_COMPARE)) return false;

        String productType = dynamicRule.getArguments()[0];
        String transactionType = dynamicRule.getArguments()[1];
        String comparator = dynamicRule.getArguments()[2];
        int thresholdValue = Integer.parseInt(dynamicRule.getArguments()[3]);

        int sumTransactions = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(uuid, transactionType, productType);

        switch (comparator) {
            case ">":
                return dynamicRule.isNegate() ? sumTransactions <= thresholdValue : sumTransactions > thresholdValue;
            case "<":
                return dynamicRule.isNegate() ? sumTransactions >= thresholdValue : sumTransactions < thresholdValue;
            case ">=":
                return dynamicRule.isNegate() ? sumTransactions < thresholdValue : sumTransactions >= thresholdValue;
            case "<=":
                return dynamicRule.isNegate() ? sumTransactions > thresholdValue : sumTransactions <= thresholdValue;
            default:
                return dynamicRule.isNegate() ? sumTransactions != thresholdValue : sumTransactions == thresholdValue;
        }
    }
}
