package com.starbank.teamproject.repository;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;


@Repository
public class RecommendationsRepository {
    private final JdbcTemplate jdbcTemplate;

    public RecommendationsRepository(@Qualifier("recommendationsJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Cacheable("users")
    public boolean CheckProductExistencesByType(UUID user, String productType) {
        Integer result = jdbcTemplate.queryForObject(
                """
                        SELECT COUNT(*)
                        FROM transactions t                     
                        JOIN PRODUCTS p ON t.PRODUCT_ID = p.id
                        WHERE t.user_id = ?
                          AND p.type = ?
                        """,
                Integer.class, user, productType);
        return result > 0;
    }

    @Cacheable("users")
    public boolean CheckProductNONExistencesByType(UUID user, String productType) {
        Integer result = jdbcTemplate.queryForObject(
                """
                        SELECT COUNT(*)
                        FROM transactions t
                        JOIN PRODUCTS p ON t.PRODUCT_ID = p.id
                        WHERE t.user_id = ?
                          AND p.type <> ?
                        """,
                Integer.class, user, productType);
        return result > 0;
    }

    @Cacheable("users")
    public Integer CheckSumTransactionByTransactionTypeAndByProductType(UUID user, String transactionType, String productType) {
        Integer result = jdbcTemplate.queryForObject(
                """
                        SELECT COALESCE(SUM(t.amount), 0)
                        FROM transactions t
                        JOIN PRODUCTS p ON t.PRODUCT_ID = p.ID
                        JOIN USERS u ON t.USER_ID  = u.ID
                        WHERE u.ID = ?
                        AND t."TYPE" = ?
                        AND p."TYPE"  = ?
                        """,
                Integer.class, user, transactionType, productType);
        return result;
    }
}
