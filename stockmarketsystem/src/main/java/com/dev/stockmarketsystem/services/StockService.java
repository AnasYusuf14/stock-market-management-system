package com.dev.stockmarketsystem.services;

import com.dev.stockmarketsystem.models.Stock;
import com.dev.stockmarketsystem.repositories.StockRepository;
import com.dev.stockmarketsystem.utils.EmailSender;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {


    private final StockRepository stockRepository;
    private final EmailSender emailSender;

    public StockService(StockRepository stockRepository, EmailSender emailSender) {
        this.stockRepository = stockRepository;
        this.emailSender = emailSender;
    }

    //Add new stock
    public Stock addStock(Stock stock) {
        if (stockRepository.findByName(stock.getName()) != null) {
            throw new IllegalArgumentException("Stock already exists!");
        }
        return stockRepository.save(stock);
    }

    // Update stock price
    public Stock updateStockPrice(Long stockId, double newPrice) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found!"));
        stock.setPrice(newPrice);
        return stockRepository.save(stock);
    }

    // Update stock quantity
    public Stock updateStockQuantity(Long stockId, int newQuantity) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found!"));
        stock.setQuantity(newQuantity);
        return stockRepository.save(stock);
    }

    // Toggle stock status
    public Stock toggleStockStatus(Long stockId) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found!"));
        stock.setActive(!stock.isActive());
        return stockRepository.save(stock);
    }

    // Get all stocks
    public List<Stock> getAllStocks() {
        return stockRepository.findAll();
    }

    public Stock getStockById(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Stock not found!"));
    }

    public List<Stock> getActiveStocks() {
        return stockRepository.findByIsActive(true);
    }

    public void deactivateStock(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with ID: " + id));
        stock.setActive(false);
        stockRepository.save(stock);
    }

    public boolean toggleActiveStatus(Long id) {
        Stock stock = stockRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Stock not found with id: " + id));

        stock.setActive(!stock.isActive());
        stockRepository.save(stock);

        return stock.isActive();
    }
    // Method to update stock status
    public Stock updateStockStatus(Long stockId, boolean isActive) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new RuntimeException("Stock not found with ID: " + stockId));
        stock.setActive(isActive);
        return stockRepository.save(stock);
    }
    // Method to check stock price
    public void checkStockPrice(String symbol, double thresholdPrice, String email) {
        Optional<Stock> stockOptional = stockRepository.findBySymbol(symbol);

        if (stockOptional.isPresent()) {
            Stock stock = stockOptional.get();
            if (stock.getPrice() > thresholdPrice) {
                String subject = "Stock Price Alert for " + symbol;
                String body = "The stock price of " + symbol + " has exceeded the threshold of " + thresholdPrice +
                        ". Current price: " + stock.getPrice();
                emailSender.sendEmail(email, subject, body);
            }
        } else {
            throw new IllegalArgumentException("Stock not found with symbol: " + symbol);
        }
    }
}
