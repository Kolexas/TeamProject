package com.starbank.teamproject.repository;

import com.starbank.teamproject.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    void deleteById(Long id);
    Optional<Statistics> findByRuleId(Long ruleId);
}
