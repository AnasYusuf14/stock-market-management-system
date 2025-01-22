package com.dev.stockmarketsystem.services;

import com.dev.stockmarketsystem.models.*;
import com.dev.stockmarketsystem.repositories.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final StockRepository stockRepository;
    private final PortfolioRepository portfolioRepository;
    private final PortfolioStockRepository portfolioStockRepository;

    public TransactionService(TransactionRepository transactionRepository,
                              StockRepository stockRepository,
                              PortfolioRepository portfolioRepository,
                              PortfolioStockRepository portfolioStockRepository) {
        this.transactionRepository = transactionRepository;
        this.stockRepository = stockRepository;
        this.portfolioRepository = portfolioRepository;
        this.portfolioStockRepository = portfolioStockRepository;
    }

    // Buy stock
    public Transaction buyStock(Long userId, Long stockId, int quantity) {
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found!"));
        // Fetch portfolio
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found!"));
        //check stock available
        double totalPrice = stock.getPrice() * quantity;
        if (portfolio.getBalance() < totalPrice) {
            throw new IllegalArgumentException("Insufficient balance!");
        }
        if (stock.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough stock available!");
        }

        // Update stock quantity and portfolio balance
        stock.setQuantity(stock.getQuantity() - quantity);
        portfolio.setBalance(portfolio.getBalance() - totalPrice);

        stockRepository.save(stock);
        portfolioRepository.save(portfolio);

        portfolioStockRepository.findByPortfolioIdAndStockId(portfolio.getId(), stock.getId())
                .ifPresentOrElse(
                        ps -> ps.setQuantity(ps.getQuantity() + quantity), // Update quantity if exists
                        () -> { // Create new PortfolioStock if not exists
                            PortfolioStock newPortfolioStock = new PortfolioStock(portfolio, stock, quantity);
                            portfolioStockRepository.save(newPortfolioStock);
                        }
                );


        // Create transaction
        Transaction transaction = new Transaction();
        transaction.setUser(portfolio.getUser());
        transaction.setStock(stock);
        transaction.setType(TransactionType.BUY);
        transaction.setQuantity(quantity);
        transaction.setPrice(stock.getPrice());
        transaction.setCommission(totalPrice * 0.05);
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }
    //Sell Stock
    public Transaction sellStock(Long userId, Long stockId, int quantity) {
        // Fetch stock
        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found!"));

        // Fetch portfolio
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found!"));

        // Fetch PortfolioStock
        PortfolioStock portfolioStock = portfolioStockRepository.findByPortfolioIdAndStockId(portfolio.getId(), stock.getId())
                .orElseThrow(() -> new IllegalArgumentException("Stock not found in user's portfolio!"));

        // Validate stock quantity in the portfolio
        if (portfolioStock.getQuantity() < quantity) {
            throw new IllegalArgumentException("Insufficient stock quantity in portfolio!");
        }

        // Calculate total sale and update balances
        double totalSale = stock.getPrice() * quantity;
        portfolio.setBalance(portfolio.getBalance() + totalSale);
        stock.setQuantity(stock.getQuantity() + quantity);
        portfolioStock.setQuantity(portfolioStock.getQuantity() - quantity);

        // Save updated portfolioStock or delete if quantity reaches zero
        if (portfolioStock.getQuantity() == 0) {
            portfolioStockRepository.delete(portfolioStock);
        } else {
            portfolioStockRepository.save(portfolioStock);
        }

        // Save stock and portfolio
        stockRepository.save(stock);
        portfolioRepository.save(portfolio);

        // Create and save the transaction
        Transaction transaction = new Transaction();
        transaction.setUser(portfolio.getUser());
        transaction.setStock(stock);
        transaction.setType(TransactionType.SELL);
        transaction.setQuantity(quantity);
        transaction.setPrice(stock.getPrice());
        transaction.setCommission(totalSale * 0.05); // Assuming 5% commission
        transaction.setTransactionDate(LocalDateTime.now());

        return transactionRepository.save(transaction);
    }

    //  Get Transactions by User
    public List<Transaction> getTransactionsByUserId(Long userId) {
        return transactionRepository.findByUserId(userId);
    }
}
