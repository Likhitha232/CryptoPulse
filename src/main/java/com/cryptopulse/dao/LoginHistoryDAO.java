package com.cryptopulse.dao;

import com.cryptopulse.model.LoginHistory;
import com.cryptopulse.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginHistoryDAO {

    // Record a login event for a user
    public void recordLogin(int userId) {
        String sql = "INSERT INTO login_history (user_id) VALUES (?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get recent login history for a user (most recent first)
    public List<LoginHistory> getLoginHistory(int userId) {
        List<LoginHistory> history = new ArrayList<>();
        String sql = "SELECT * FROM login_history WHERE user_id = ? ORDER BY login_time DESC LIMIT 10";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LoginHistory entry = new LoginHistory();
                entry.setId(rs.getInt("id"));
                entry.setUserId(rs.getInt("user_id"));
                entry.setLoginTime(rs.getTimestamp("login_time"));
                history.add(entry);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return history;
    }
}