package com.dev.stockmarketsystem.controllers;

import com.dev.stockmarketsystem.models.PriceHistory;
import com.dev.stockmarketsystem.models.Stock;
import com.dev.stockmarketsystem.services.AlphaVantageService;
import com.dev.stockmarketsystem.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stocks")
public class StockController {

    private final StockService stockService;
    @Autowired
    private AlphaVantageService alphaVantageService;

    public StockController(StockService stockService) {
        this.stockService = stockService;
    }


    @GetMapping("/update")
    public String updateStockData(@RequestParam List<String> symbols) {
        alphaVantageService.fetchAndSaveStocks(symbols);
        return "Stock data for symbols " + symbols + " has been updated!";
    }



    // Add a new stock
    @PostMapping("/add")
    public ResponseEntity<Stock> addStock(@RequestBody Stock stock) {
        Stock addedStock = stockService.addStock(stock);
        return ResponseEntity.ok(addedStock);
    }

    //  Update stock price
    @PutMapping("/update-price/{id}")
    public ResponseEntity<Stock> updateStockPrice(@PathVariable Long id, @RequestParam double newPrice) {
        Stock updatedStock = stockService.updateStockPrice(id, newPrice);
        return ResponseEntity.ok(updatedStock);
    }

    //  Update stock quantity
    @PutMapping("/update-quantity/{id}")
    public ResponseEntity<Stock> updateStockQuantity(@PathVariable Long id, @RequestParam int newQuantity) {
        Stock updatedStock = stockService.updateStockQuantity(id, newQuantity);
        return ResponseEntity.ok(updatedStock);
    }

    // Fetch all stocks
    @GetMapping("/all")
    public ResponseEntity<List<Stock>> getAllStocks() {
        List<Stock> stocks = stockService.getAllStocks();
        return ResponseEntity.ok(stocks);
    }

    // 5. Fetch stock by ID
    @GetMapping("/{id}")
    public ResponseEntity<Stock> getStockById(@PathVariable Long id) {
        Stock stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }
    //Get active stocks
    @GetMapping("/active")
    public ResponseEntity<List<Stock>> getActiveStocks() {
        List<Stock> activeStocks = stockService.getActiveStocks();
        return ResponseEntity.ok(activeStocks);
    }
    @PutMapping("/deactivate/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deactivateStock(@PathVariable Long id) {
        stockService.deactivateStock(id);
        return ResponseEntity.ok("Stock with ID " + id + " has been deactivated.");
    }
    @PutMapping("/{id}/toggle-active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> toggleStockActive(@PathVariable Long id) {
        boolean isActive = stockService.toggleActiveStatus(id);
        return ResponseEntity.ok("Stock status updated. Active: " + isActive);
    }
    // Endpoint to update stock status
    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')") // Ensure only admin can access this
    public ResponseEntity<Stock> updateStockStatus(@PathVariable Long id, @RequestParam boolean isActive) {
        Stock updatedStock = stockService.updateStockStatus(id, isActive);
        return ResponseEntity.ok(updatedStock);
    }





}

