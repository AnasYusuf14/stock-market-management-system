package com.dev.stockmarketsystem.repositories;

import com.dev.stockmarketsystem.models.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {

    // Find price history by stock symbol
    List<PriceHistory> findByStockSymbol(String symbol);

    // Find price history by stock symbol and date range
    List<PriceHistory> findByStockSymbolAndTimestampBetween(String symbol, LocalDateTime startDate, LocalDateTime endDate);
}

