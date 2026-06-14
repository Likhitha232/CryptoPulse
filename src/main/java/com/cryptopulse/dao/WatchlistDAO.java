package com.cryptopulse.dao;

import com.cryptopulse.model.WatchlistItem;
import com.cryptopulse.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WatchlistDAO {

    // Add a coin to user's watchlist. Returns false if already exists.
    public boolean addToWatchlist(int userId, String coinId) {
        String checkSql = "SELECT id FROM watchlist WHERE user_id = ? AND coin_id = ?";
        String insertSql = "INSERT INTO watchlist (user_id, coin_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.getConnection()) {

            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setInt(1, userId);
                checkStmt.setString(2, coinId);
                ResultSet rs = checkStmt.executeQuery();
                if (rs.next()) {
                    return false; // already in watchlist
                }
            }

            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setInt(1, userId);
                insertStmt.setString(2, coinId);
                insertStmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Remove a coin from user's watchlist
    public boolean removeFromWatchlist(int userId, String coinId) {
        String sql = "DELETE FROM watchlist WHERE user_id = ? AND coin_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            stmt.setString(2, coinId);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all watchlist items for a user
    public List<WatchlistItem> getWatchlist(int userId) {
        List<WatchlistItem> list = new ArrayList<>();
        String sql = "SELECT * FROM watchlist WHERE user_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                WatchlistItem item = new WatchlistItem();
                item.setId(rs.getInt("id"));
                item.setUserId(rs.getInt("user_id"));
                item.setCoinId(rs.getString("coin_id"));
                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }
}