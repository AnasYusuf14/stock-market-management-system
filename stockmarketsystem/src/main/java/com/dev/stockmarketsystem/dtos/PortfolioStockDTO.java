package com.dev.stockmarketsystem.dtos;

public class PortfolioStockDTO {
    private Long stockId;
    private String stockName;
    private int quantity;

    public PortfolioStockDTO(Long stockId, String stockName, int quantity) {
        this.stockId = stockId;
        this.stockName = stockName;
        this.quantity = quantity;
    }

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}
