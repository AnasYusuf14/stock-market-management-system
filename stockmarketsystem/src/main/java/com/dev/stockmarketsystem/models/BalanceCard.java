package com.dev.stockmarketsystem.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "balance_cards")
public class BalanceCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false , name = "is_used")
    private Boolean used = false;
    // No-arg constructor
    public BalanceCard() {
    }

    // All-arg constructor
    public BalanceCard(Long id, String code, Double amount, Boolean used) {
        this.id = id;
        this.code = code;
        this.amount = amount;
        this.used = used;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Boolean getUsed() {
        return used;
    }

    public void setUsed(Boolean used) {
        this.used = used;
    }

    // toString method
    @Override
    public String toString() {
        return "BalanceCard{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", amount=" + amount +
                ", used=" + used +
                '}';
    }

    // equals method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BalanceCard that = (BalanceCard) o;

        if (!id.equals(that.id)) return false;
        if (!code.equals(that.code)) return false;
        if (!amount.equals(that.amount)) return false;
        return used.equals(that.used);
    }

    // hashCode method
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + code.hashCode();
        result = 31 * result + amount.hashCode();
        result = 31 * result + used.hashCode();
        return result;
    }
}

