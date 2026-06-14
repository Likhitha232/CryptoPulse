package com.cryptopulse.model;

public class WatchlistItem {
    private int id;
    private int userId;
    private String coinId;

    public WatchlistItem() {}

    public WatchlistItem(int id, int userId, String coinId) {
        this.id = id;
        this.userId = userId;
        this.coinId = coinId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getCoinId() { return coinId; }
    public void setCoinId(String coinId) { this.coinId = coinId; }
}
