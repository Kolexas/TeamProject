package com.starbank.teamproject.repository;

import com.starbank.teamproject.model.RecommendationDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class RecommendationRuleInvest500 implements RecommendationRuleSet {

    private RecommendationsRepository recommendationsRepository;

    public RecommendationRuleInvest500(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDTO> CheckRule(UUID userId) {
        boolean First_Rule = recommendationsRepository.CheckProductExistencesByType(userId, "DEBIT");
        boolean Second_Rule = recommendationsRepository.CheckProductExistencesByType(userId, "INVEST");
        boolean Third_Rule = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(userId, "DEPOSIT", "SAVING") > 1_000;


        if (First_Rule && !Second_Rule && Third_Rule) {
            return Optional.of(new RecommendationDTO(userId, "Invest 500", "Откройте свой путь к успеху с индивидуальным инвестиционным счетом (ИИС) от нашего банка! " +
                    "Воспользуйтесь налоговыми льготами и начните инвестировать с умом. Пополните счет до конца года и получите выгоду в виде вычета на взнос в следующем налоговом " +
                    "периоде. Не упустите возможность разнообразить свой портфель, снизить риски и следить за актуальными рыночными тенденциями. Откройте ИИС сегодня и станьте ближе к финансовой независимости!"));
        }
        return Optional.empty();
    }
}
