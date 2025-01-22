package com.dev.stockmarketsystem.services;

import com.dev.stockmarketsystem.models.PriceHistory;
import com.dev.stockmarketsystem.repositories.PriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;

    public PriceHistoryService(PriceHistoryRepository priceHistoryRepository) {
        this.priceHistoryRepository = priceHistoryRepository;
    }

    // Fetch price history by symbol
    public List<PriceHistory> getPriceHistoryBySymbol(String symbol) {
        return priceHistoryRepository.findByStockSymbol(symbol);
    }

    // Fetch price history by symbol and date range
    public List<PriceHistory> getPriceHistoryBySymbolAndDateRange(String symbol, LocalDateTime startDate, LocalDateTime endDate) {
        return priceHistoryRepository.findByStockSymbolAndTimestampBetween(symbol, startDate, endDate);
    }

    // Fetch all price history
    public List<PriceHistory> getAllPriceHistories() {
        return priceHistoryRepository.findAll();
    }
}
