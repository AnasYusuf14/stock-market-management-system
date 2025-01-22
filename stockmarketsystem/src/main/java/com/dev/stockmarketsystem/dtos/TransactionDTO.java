public class TransactionDTO {
    private Long id;
    private String type;
    private int quantity;
    private double price;
    private double commission;
    private Long stockId;
    private String stockName;

    public TransactionDTO(Long id, String type, int quantity, double price, double commission, Long stockId, String stockName) {
        this.id = id;
        this.type = type;
        this.quantity = quantity;
        this.price = price;
        this.commission = commission;
        this.stockId = stockId;
        this.stockName = stockName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
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

    public Long getStockId() {
        return stockId;
    }

    public void setStockId(Long stockId) {
        this.stockId = stockId;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }
}

