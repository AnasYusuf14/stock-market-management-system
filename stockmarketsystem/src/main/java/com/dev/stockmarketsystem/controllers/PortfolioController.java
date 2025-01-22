package com.dev.stockmarketsystem.controllers;

import com.dev.stockmarketsystem.dtos.PortfolioStockDTO;
import com.dev.stockmarketsystem.models.Portfolio;
import com.dev.stockmarketsystem.services.PortfolioService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/portfolios")
public class PortfolioController {

    private final PortfolioService portfolioService;

    public PortfolioController(PortfolioService portfolioService) {
        this.portfolioService = portfolioService;
    }

    // Create a portfolio for a user
    @PostMapping("/create/{userId}")
    public ResponseEntity<Portfolio> createPortfolio(@PathVariable Long userId) {
        Portfolio createdPortfolio = portfolioService.createPortfolio(userId);
        return ResponseEntity.ok(createdPortfolio);
    }

    // Fetch a portfolio by user ID
    @GetMapping("/user/{userId}")
    public ResponseEntity<Portfolio> getPortfolioByUserId(@PathVariable Long userId) {
        Portfolio portfolio = portfolioService.findByUserId(userId);
        return ResponseEntity.ok(portfolio);
    }

    //  Update portfolio balance
    @PutMapping("/update-balance/{portfolioId}")
    public ResponseEntity<Portfolio> updatePortfolioBalance(
            @PathVariable Long portfolioId,
            @RequestParam double amount) {
        Portfolio updatedPortfolio = portfolioService.updateBalance(portfolioId, amount);
        return ResponseEntity.ok(updatedPortfolio);
    }

    //  Fetch all portfolios (Optional)
    @GetMapping("/all")
    public ResponseEntity<List<Portfolio>> getAllPortfolios() {
        List<Portfolio> portfolios = portfolioService.getAllPortfolios();
        return ResponseEntity.ok(portfolios);
    }
    // واجهة برمجية لجلب الأسهم المرتبطة بمحفظة معينة
    @GetMapping("/{portfolioId}/stocks")
    public ResponseEntity<List<PortfolioStockDTO>> getStocksByPortfolioId(@PathVariable Long portfolioId) {
        List<PortfolioStockDTO> portfolioStocks = portfolioService.getStocksByPortfolioId(portfolioId);
        return ResponseEntity.ok(portfolioStocks);
    }
}
