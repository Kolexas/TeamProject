package com.starbank.teamproject.repository;

import com.starbank.teamproject.model.RecommendationDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationRuleTopSaving implements RecommendationRuleSet {
    private RecommendationsRepository recommendationsRepository;

    public RecommendationRuleTopSaving(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDTO> CheckRule(UUID userId) {
        boolean first_Rule = recommendationsRepository.CheckProductExistencesByType(userId, "DEBIT");
        Boolean second_RuleFirstPart = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(userId, "DEPOSIT", "DEBIT") > 50_000;
        Boolean second_RuleSecondPart = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(userId, "DEPOSIT", "SAVING") > 50_000;
        Integer third_RuleSavingSum = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(userId, "DEPOSIT", "DEBIT");
        Integer third_RuleWithdrawSum = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(userId, "WITHDRAW", "DEBIT");
        boolean Third_Rule = third_RuleSavingSum - third_RuleWithdrawSum > 0;
        if (first_Rule && (second_RuleFirstPart || second_RuleSecondPart) && Third_Rule) {
            return Optional.of(new RecommendationDTO(userId, "TopSaving", "Откройте свою собственную «Копилку» c нашим банком! «Копилка» - это уникальный банковский инструмент, " +
                    "который поможет вам легко и удобно накапливать деньги на важные цели. Больше никаких забытый чеков и потерянных квитанций - всё под контролем!" +
                    "Преимущества «Копилки». " +
                    "Накопление средств на конкретные цели. " +
                    "Установить лимит и срок накопления, и банк будет автоматически переводить определённую сумму на ваш счёт." +
                    "Прозрачность и контроль." +
                    "Отслеживайте свои доходы и расходы, контролируйте процесс накопления и корректируйте стратегию при необходимости." +
                    "Безопасность и надежность." +
                    "Ваши средства находятся под защитой банка, а доступ к ним возможен только через мобильное приложение или интернет-банкинг." +
                    "Начните использовать «Копилку» уже сегодня и станьте ближе к своим финансовым целям!" +
                    " "));
        }
        return Optional.empty();
    }
}
