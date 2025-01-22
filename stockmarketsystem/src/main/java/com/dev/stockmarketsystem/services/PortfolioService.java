package com.dev.stockmarketsystem.services;

import com.dev.stockmarketsystem.dtos.PortfolioStockDTO;
import com.dev.stockmarketsystem.models.Portfolio;
import com.dev.stockmarketsystem.models.PortfolioStock;
import com.dev.stockmarketsystem.models.Stock;
import com.dev.stockmarketsystem.models.User;
import com.dev.stockmarketsystem.repositories.PortfolioRepository;
import com.dev.stockmarketsystem.repositories.PortfolioStockRepository;
import com.dev.stockmarketsystem.repositories.StockRepository;
import com.dev.stockmarketsystem.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userReository;
    private final StockRepository stockRepository;
    private final PortfolioStockRepository portfolioStockRepository;

    public PortfolioService(PortfolioRepository portfolioRepository
            , UserRepository userReository
            , StockRepository stockRepository
            , PortfolioStockRepository portfolioStockRepository) {
        this.portfolioRepository = portfolioRepository;
        this.userReository = userReository;
        this.stockRepository = stockRepository;
        this.portfolioStockRepository = portfolioStockRepository;
    }

    // Create new portfolio
    public Portfolio createPortfolio(Long userId) {
        User user = userReository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found!"));
        Portfolio portfolio = new Portfolio();
        portfolio.setUser(user);
        portfolio.setBalance(0.0);
        return portfolioRepository.save(portfolio);
    }

    // Update portfolio balance
    public Portfolio updateBalance(Long portfolioId, double amount) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found!"));
        portfolio.setBalance(portfolio.getBalance() + amount);
        return portfolioRepository.save(portfolio);
    }

    // Get portfolio by user id
    public Portfolio findByUserId(Long userId) {
        return portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found!"));
    }

    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }
    public PortfolioStock addStockToPortfolio(Long portfolioId, Long stockId, int quantity) {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found!"));

        Stock stock = stockRepository.findById(stockId)
                .orElseThrow(() -> new IllegalArgumentException("Stock not found!"));

        PortfolioStock portfolioStock = new PortfolioStock(portfolio, stock, quantity);
        return portfolioStockRepository.save(portfolioStock);
    }
    // طريقة لاسترجاع الأسهم المرتبطة بمحفظة
    public List<PortfolioStockDTO> getStocksByPortfolioId(Long portfolioId) {
        return portfolioStockRepository.findStocksByPortfolioId(portfolioId);
    }




}

