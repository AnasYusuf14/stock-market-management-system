package com.dev.stockmarketsystem.controllers;

import com.dev.stockmarketsystem.models.PriceHistory;
import com.dev.stockmarketsystem.services.PriceHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/price-history")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    @Autowired
    public PriceHistoryController(PriceHistoryService priceHistoryService) {
        this.priceHistoryService = priceHistoryService;
    }

    // 1. Get price history by stock symbol
    @GetMapping("/{symbol}")
    public ResponseEntity<List<PriceHistory>> getPriceHistoryBySymbol(@PathVariable String symbol) {
        List<PriceHistory> priceHistories = priceHistoryService.getPriceHistoryBySymbol(symbol);
        return ResponseEntity.ok(priceHistories);
    }

    // 2. Get price history by stock symbol and date range
    @GetMapping("/{symbol}/range")
    public ResponseEntity<List<PriceHistory>> getPriceHistoryBySymbolAndDateRange(
            @PathVariable String symbol,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate) {
        List<PriceHistory> priceHistories = priceHistoryService.getPriceHistoryBySymbolAndDateRange(symbol, startDate, endDate);
        return ResponseEntity.ok(priceHistories);
    }

    // 3. Get all price history (Admin only)
    @GetMapping("/all")
    public ResponseEntity<List<PriceHistory>> getAllPriceHistories() {
        List<PriceHistory> priceHistories = priceHistoryService.getAllPriceHistories();
        return ResponseEntity.ok(priceHistories);
    }
}
