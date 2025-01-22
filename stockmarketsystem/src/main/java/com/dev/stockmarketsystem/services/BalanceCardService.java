package com.dev.stockmarketsystem.services;

import com.dev.stockmarketsystem.models.BalanceCard;
import com.dev.stockmarketsystem.models.Portfolio;
import com.dev.stockmarketsystem.repositories.BalanceCardRepository;
import com.dev.stockmarketsystem.repositories.PortfolioRepository;
import org.springframework.stereotype.Service;

@Service
public class BalanceCardService {

    private final BalanceCardRepository balanceCardRepository;
    private final PortfolioRepository portfolioRepository;

    public BalanceCardService(BalanceCardRepository balanceCardRepository, PortfolioRepository portfolioRepository) {
        this.balanceCardRepository = balanceCardRepository;
        this.portfolioRepository = portfolioRepository;
    }

    // Create a new balance card
    public BalanceCard createBalanceCard(String code, double amount) {
        BalanceCard card = new BalanceCard();
        card.setCode(code);
        card.setAmount(amount);
        card.setUsed(false);
        return balanceCardRepository.save(card);
    }

    // Use balance card
    public String useBalanceCard(String code, Long portfolioId) {
        BalanceCard card = balanceCardRepository.findByCode(code)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card code!"));
        if (card.getUsed()) {
            throw new IllegalArgumentException("This card has already been used.");
        }

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found."));

        portfolio.setBalance(portfolio.getBalance() + card.getAmount());
        portfolioRepository.save(portfolio);

        card.setUsed(true);
        balanceCardRepository.save(card);

        return "Balance updated successfully!";
    }

    public void assignBalanceToUser(Long userId, double balance) {
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found for user ID: " + userId));

        // Add balance to the user's portfolio
        portfolio.setBalance(portfolio.getBalance() + balance);

        // Save the updated portfolio
        portfolioRepository.save(portfolio);
    }

    public double getBalance(Long userId) {
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found for user ID: " + userId));

        // Return the user's balance
        return portfolio.getBalance();
    }
    // Redeem balance card
    public void redeemCard(String cardCode, Long userId) {
        // Check if the card exists and is not used
        BalanceCard card = balanceCardRepository.findByCode(cardCode)
                .orElseThrow(() -> new IllegalArgumentException("Invalid card code!"));

        if (card.getUsed()) {
            throw new IllegalArgumentException("This card has already been used.");
        }

        // Fetch the user's portfolio
        Portfolio portfolio = portfolioRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found for user ID: " + userId));

        // Update portfolio balance
        portfolio.setBalance(portfolio.getBalance() + card.getAmount());
        portfolioRepository.save(portfolio);

        // Mark the card as used
        card.setUsed(true);
        balanceCardRepository.save(card);
    }

}
