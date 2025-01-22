package com.dev.stockmarketsystem.repositories;

import com.dev.stockmarketsystem.models.BalanceCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BalanceCardRepository extends JpaRepository<BalanceCard, Long> {
    Optional<BalanceCard> findByCode(String code);
}

