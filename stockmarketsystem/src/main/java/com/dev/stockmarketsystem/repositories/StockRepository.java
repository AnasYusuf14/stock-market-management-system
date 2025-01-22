package com.dev.stockmarketsystem.repositories;

import com.dev.stockmarketsystem.models.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {
    Stock findByName(String name);

    Optional<Stock> findBySymbol(String symbol);

    List<Stock> findByIsActive(boolean isActive);
}

