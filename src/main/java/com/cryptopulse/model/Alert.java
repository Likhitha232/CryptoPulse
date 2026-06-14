package com.cryptopulse.model;

import java.math.BigDecimal;

public class Alert {
    private int id;
    private int userId;
    private String coinId;
    private BigDecimal targetPrice;

    public Alert() {}

    public Alert(int id, int userId, String coinId, BigDecimal targetPrice) {
        this.id = id;
        this.userId = userId;
        this.coinId = coinId;
        this.targetPrice = targetPrice;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCoinId() { return coinId; }
    public void setCoinId(String coinId) { this.coinId = coinId; }

    public BigDecimal getTargetPrice() { return targetPrice; }
    public void setTargetPrice(BigDecimal targetPrice) { this.targetPrice = targetPrice; }
}