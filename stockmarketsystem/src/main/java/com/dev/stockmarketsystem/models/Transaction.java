package com.dev.stockmarketsystem.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
@Table(name = "transactions")
@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // References the User entity
    private User user;

    @ManyToOne
    @JoinColumn(name = "stock_id", nullable = false) // References the Stock entity
    private Stock stock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType type; // BUY or SELL

    @Column(nullable = false)
    private int quantity; // Quantity of stocks in this transaction

    @Column(nullable = false)
    private double price; // Price per stock during the transaction

    @Column(nullable = false)
    private double commission; // Commission fee for the transaction

    @Column(name = "transaction_date",nullable = false)
    private LocalDateTime transactionDate ;// Date and time of the transaction

    // Default constructor
    public Transaction() {}

    public Transaction(Long id, User user, Stock stock, TransactionType type, int quantity, double price, double commission, LocalDateTime transactionDate) {
        this.id = id;
        this.user = user;
        this.stock = stock;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.commission = commission;
        this.transactionDate = LocalDateTime.now(); ;
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

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getCommission() {
        return commission;
    }

    public void setCommission(double commission) {
        this.commission = commission;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", user=" + user +
                ", stock=" + stock +
                ", type=" + type +
                ", quantity=" + quantity +
                ", price=" + price +
                ", commission=" + commission +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
