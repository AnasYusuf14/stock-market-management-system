package com.dev.stockmarketsystem.services;

import com.dev.stockmarketsystem.models.PriceHistory;
import com.dev.stockmarketsystem.models.Stock;
import com.dev.stockmarketsystem.repositories.PriceHistoryRepository;
import com.dev.stockmarketsystem.repositories.StockRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AlphaVantageService {

    private final String API_KEY = "4599OR2R7CA8PNKH"; // Replace with your API key
    private final String BASE_URL = "https://www.alphavantage.co/query";

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    /**
     * Fetch stock details for multiple symbols and save/update them in the database.
     */
    public void fetchAndSaveStocks(List<String> symbols) {
        RestTemplate restTemplate = new RestTemplate();
        ObjectMapper objectMapper = new ObjectMapper();

        for (String symbol : symbols) {
            try {
                String url = BASE_URL + "?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + API_KEY;
                String jsonResponse = restTemplate.getForObject(url, String.class);

                JsonNode rootNode = objectMapper.readTree(jsonResponse);
                JsonNode globalQuote = rootNode.path("Global Quote");

                if (globalQuote != null && !globalQuote.isEmpty()) {
                    String stockSymbol = globalQuote.get("01. symbol").asText();
                    double price = globalQuote.get("05. price").asDouble();
                    String stockName = "Unknown"; // Global Quote doesn't provide a stock name.

                    Stock stock = stockRepository.findBySymbol(stockSymbol).orElse(new Stock());
                    stock.setSymbol(stockSymbol);
                    stock.setName(stockName); // You can replace this with the actual name if available from another API.
                    stock.setPrice(price);
                    stock.setQuantity(stock.getQuantity() == 0 ? 10000 : stock.getQuantity()); // Default to 10000 if not set.
                    stock.setActive(true);

                    stockRepository.save(stock);

                    // Save to price history
                    savePriceHistory(stock, price);
                } else {
                    System.out.println("No data found for symbol: " + symbol);
                }
            } catch (Exception e) {
                System.out.println("Error fetching data for symbol: " + symbol);
                e.printStackTrace();
            }
        }
    }

    /**
     * Update prices for all active stocks in the database.
     */
    @Scheduled(fixedRate = 3600000) // Run every hour (3600000 milliseconds)
    public void updateStockPrices() {
        Iterable<Stock> stocks = stockRepository.findAll();

        for (Stock stock : stocks) {
            if (stock.isActive()) {
                String symbol = stock.getSymbol();
                try {
                    String url = BASE_URL + "?function=GLOBAL_QUOTE&symbol=" + symbol + "&apikey=" + API_KEY;
                    RestTemplate restTemplate = new RestTemplate();
                    String jsonResponse = restTemplate.getForObject(url, String.class);

                    ObjectMapper objectMapper = new ObjectMapper();
                    JsonNode rootNode = objectMapper.readTree(jsonResponse);
                    JsonNode globalQuote = rootNode.path("Global Quote");

                    if (globalQuote != null && !globalQuote.isEmpty()) {
                        double price = globalQuote.get("05. price").asDouble();
                        stock.setPrice(price);
                        stockRepository.save(stock);

                        // Save to price history
                        savePriceHistory(stock, price);

                        System.out.println("Updated price for stock: " + symbol + " to " + price);
                    } else {
                        System.out.println("No data found for symbol: " + symbol);
                    }
                } catch (Exception e) {
                    System.out.println("Error updating price for stock: " + symbol);
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Save price history for a stock.
     */
    private void savePriceHistory(Stock stock, double price) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setStock(stock);
        priceHistory.setPrice(price);
        priceHistory.setTimestamp(LocalDateTime.now());

        priceHistoryRepository.save(priceHistory);
    }
}
