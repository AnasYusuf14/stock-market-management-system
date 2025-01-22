package com.dev.stockmarketsystem.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import com.dev.stockmarketsystem.models.Portfolio;
@Entity
@Table(name = "portfolio_stocks")
public class PortfolioStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "portfolio_id")
    @JsonIgnore // Prevents serialization of Portfolio
    @JsonBackReference
    private Portfolio portfolio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stock_id")
    private Stock stock;

    private int quantity;

    public PortfolioStock() {}

    public PortfolioStock(Portfolio portfolio,Stock stock, int quantity) {
        this.portfolio = portfolio;
        this.stock = stock;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Portfolio getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(Portfolio portfolio) {
        this.portfolio = portfolio;
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "PortfolioStock{" +
                "id=" + id +
                ", portfolio=" + portfolio +
                ", stock=" + stock +
                ", quantity=" + quantity +
                '}';
    }
}

