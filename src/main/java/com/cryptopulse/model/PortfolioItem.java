package com.cryptopulse.model;

import java.math.BigDecimal;

public class PortfolioItem {
    private int id;
    private int userId;
    private String coinId;
    private BigDecimal quantity;

    public PortfolioItem() {}

    public PortfolioItem(int id, int userId, String coinId, BigDecimal quantity) {
        this.id = id;
        this.userId = userId;
        this.coinId = coinId;
        this.quantity = quantity;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCoinId() { return coinId; }
    public void setCoinId(String coinId) { this.coinId = coinId; }

    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
}
