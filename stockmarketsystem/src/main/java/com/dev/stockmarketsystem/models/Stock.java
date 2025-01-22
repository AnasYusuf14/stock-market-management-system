package com.dev.stockmarketsystem.models;

import com.dev.stockmarketsystem.models.PortfolioStock;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Table(name = "stocks")
@Entity
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private int quantity;
    @Column(name = "is_active",nullable = false)
    private boolean isActive;

    @OneToMany(mappedBy = "stock", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PortfolioStock> portfolioStocks = new HashSet<>();

    public Stock() {}

    public Stock(String name, String symbol, double price, int quantity, boolean isActive) {
        this.name = name;
        this.symbol = symbol;
        this.price = price;
        this.quantity = quantity;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Set<PortfolioStock> getPortfolioStocks() {
        return portfolioStocks;
    }

    public void setPortfolioStocks(Set<PortfolioStock> portfolioStocks) {
        this.portfolioStocks = portfolioStocks;
    }
}
