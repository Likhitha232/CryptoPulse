package com.cryptopulse.dao;

import com.cryptopulse.model.PortfolioItem;
import com.cryptopulse.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDAO {

    // Add a holding (or update quantity if coin already exists for this user)
    public boolean addHolding(int userId, String coinId, BigDecimal quantity) {
        String checkSql = "SELECT id, quantity FROM portfolio WHERE user_id = ? AND coin_id = ?";
        String updateSql = "UPDATE portfolio SET quantity = ? WHERE id = ?";
        String insertSql = "INSERT INTO portfolio (user_id, coin_id, quantity) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setString(2, coinId);
                ResultSet rs = checkStmt.executeQuery();

                if (rs.next()) {
                    // Already exists - add to existing quantity
                    int id = rs.getInt("id");
                    BigDecimal existingQty = rs.getBigDecimal("quantity");
                    BigDecimal newQty = existingQty.add(quantity);

                    try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                        updateStmt.setBigDecimal(1, newQty);
                        updateStmt.setInt(2, id);
                        updateStmt.executeUpdate();
                    }
                    return true;
                }
            }

            // Doesn't exist - insert new
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, userId);
                insertStmt.setString(2, coinId);
                insertStmt.setBigDecimal(3, quantity);
                insertStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove a holding entirely (by holding ID, only if it belongs to the user)
    public boolean removeHolding(int holdingId, int userId) {
        String sql = "DELETE FROM portfolio WHERE id = ? AND user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, holdingId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all holdings for a user
    public List<PortfolioItem> getPortfolio(int userId) {
        List<PortfolioItem> list = new ArrayList<>();
        String sql = "SELECT * FROM portfolio WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PortfolioItem item = new PortfolioItem();
                item.setId(rs.getInt("id"));
                item.setUserId(rs.getInt("user_id"));
                item.setCoinId(rs.getString("coin_id"));
                item.setQuantity(rs.getBigDecimal("quantity"));
                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}
