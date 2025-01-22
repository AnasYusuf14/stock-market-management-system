package com.dev.stockmarketsystem.models;

import com.dev.stockmarketsystem.models.PortfolioStock;
import com.dev.stockmarketsystem.models.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Table(name = "portfolios")
@Entity
public class Portfolio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "portfolio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<PortfolioStock> portfolioStocks = new HashSet<>();

    private double balance = 0.0;

    public Portfolio() {}

    public Portfolio(User user, double balance) {
        this.user = user;
        this.balance = balance;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<PortfolioStock> getPortfolioStocks() {
        return portfolioStocks;
    }

    public void setPortfolioStocks(Set<PortfolioStock> portfolioStocks) {
        this.portfolioStocks = portfolioStocks;
    }

    @Override
    public String toString() {
        return "Portfolio{" +
                "id=" + id +
                ", user=" + user +
                ", portfolioStocks=" + portfolioStocks +
                ", balance=" + balance +
                '}';
    }
}
