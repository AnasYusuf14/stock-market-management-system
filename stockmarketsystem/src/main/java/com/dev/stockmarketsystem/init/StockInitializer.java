package com.dev.stockmarketsystem.init;

import com.dev.stockmarketsystem.services.AlphaVantageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class StockInitializer implements CommandLineRunner {

    private final AlphaVantageService alphaVantageService;

    public StockInitializer(AlphaVantageService alphaVantageService) {
        this.alphaVantageService = alphaVantageService;
    }

    @Override
    public void run(String... args) throws Exception {
        // قائمة الرموز المراد جلبها
        List<String> symbols = Arrays.asList("AAPL", "MSFT", "GOOGL", "AMZN", "TSLA");
        alphaVantageService.fetchAndSaveStocks(symbols);
    }
}

