package com.starbank.teamproject.repository;

import com.starbank.teamproject.model.RecommendationDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;


@Component
public class RecommendationRuleSimpleCredit implements RecommendationRuleSet {
    private RecommendationsRepository recommendationsRepository;

    public RecommendationRuleSimpleCredit(RecommendationsRepository recommendationsRepository) {
        this.recommendationsRepository = recommendationsRepository;
    }

    @Override
    public Optional<RecommendationDTO> CheckRule(UUID userId) {

        boolean noCreditProducts = !recommendationsRepository.CheckProductExistencesByType(userId, "CREDIT");

        Integer debitDeposits = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(userId, "DEPOSIT", "DEBIT");

        Integer debitWithdrawals = recommendationsRepository.CheckSumTransactionByTransactionTypeAndByProductType(userId, "WITHDRAW", "DEBIT");

        boolean hasPositiveSavings = debitDeposits > debitWithdrawals;
        boolean withdrawalsOverLimit = debitWithdrawals > 100_000;

        if (noCreditProducts && hasPositiveSavings && withdrawalsOverLimit) {
            return Optional.of(new RecommendationDTO(
                    userId,
                    "Простой кредит",
                    """
                            Откройте мир выгодных кредитов с нами!
                            Ищете способ быстро и без лишних хлопот получить нужную сумму? Тогда наш выгодный кредит — именно то, что вам нужно! Мы предлагаем низкие процентные ставки, гибкие условия и индивидуальный подход к каждому клиенту.
                            Почему выбирают нас:
                            Быстрое рассмотрение заявки. Мы ценим ваше время, поэтому процесс рассмотрения заявки занимает всего несколько часов.
                            Удобное оформление. Подать заявку на кредит можно онлайн на нашем сайте или в мобильном приложении.
                            Широкий выбор кредитных продуктов. Мы предлагаем кредиты на различные цели: покупку недвижимости, автомобиля, образование, лечение и многое другое.
                            Не упустите возможность воспользоваться выгодными условиями кредитования от нашей компании!"""
            ));
        }
        return Optional.empty();
    }
}
