package com.starbank.teamproject.repository;

import com.starbank.teamproject.entity.Rule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RuleRepository extends JpaRepository<Rule, Long> {
    void deleteById(Long id);
}
