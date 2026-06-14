package com.cryptopulse.dao;

import com.cryptopulse.model.Alert;
import com.cryptopulse.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlertDAO {

    // Add a new price alert
    public boolean addAlert(int userId, String coinId, BigDecimal targetPrice) {
        String sql = "INSERT INTO alerts (user_id, coin_id, target_price) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, coinId);
            stmt.setBigDecimal(3, targetPrice);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove an alert (only if it belongs to the user)
    public boolean removeAlert(int alertId, int userId) {
        String sql = "DELETE FROM alerts WHERE id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, alertId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all alerts for a user
    public List<Alert> getAlerts(int userId) {
        List<Alert> list = new ArrayList<>();
        String sql = "SELECT * FROM alerts WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Alert alert = new Alert();
                alert.setId(rs.getInt("id"));
                alert.setUserId(rs.getInt("user_id"));
                alert.setCoinId(rs.getString("coin_id"));
                alert.setTargetPrice(rs.getBigDecimal("target_price"));
                list.add(alert);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}