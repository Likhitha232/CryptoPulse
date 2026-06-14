package com.cryptopulse.model;

import java.sql.Timestamp;

public class LoginHistory {
    private int id;
    private int userId;
    private Timestamp loginTime;

    public LoginHistory() {}

    public LoginHistory(int id, int userId, Timestamp loginTime) {
        this.id = id;
        this.userId = userId;
        this.loginTime = loginTime;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public Timestamp getLoginTime() { return loginTime; }
    public void setLoginTime(Timestamp loginTime) { this.loginTime = loginTime; }
}
