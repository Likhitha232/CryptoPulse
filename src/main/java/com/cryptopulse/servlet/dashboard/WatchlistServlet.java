package com.cryptopulse.servlet.dashboard;

import com.cryptopulse.dao.WatchlistDAO;
import com.cryptopulse.model.WatchlistItem;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/WatchlistServlet")
public class WatchlistServlet extends HttpServlet {

    private WatchlistDAO watchlistDAO = new WatchlistDAO();

    // GET: returns list of coin IDs in user's watchlist as JSON array
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("[]");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        List<WatchlistItem> items = watchlistDAO.getWatchlist(userId);

        List<String> coinIds = new ArrayList<>();
        for (WatchlistItem item : items) {
            coinIds.add(item.getCoinId());
        }

        Gson gson = new Gson();
        out.print(gson.toJson(coinIds));
    }

    // POST: add or remove a coin from watchlist
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        if (session == null || session.getAttribute("userId") == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.print("Please login first.");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");
        String coinId = request.getParameter("coinId");

        if (coinId == null || coinId.trim().isEmpty()) {
            out.print("Invalid coin.");
            return;
        }

        coinId = coinId.trim().toLowerCase();

        if ("remove".equals(action)) {
            watchlistDAO.removeFromWatchlist(userId, coinId);
            out.print("Removed from watchlist.");
        } else {
            boolean added = watchlistDAO.addToWatchlist(userId, coinId);
            if (added) {
                out.print("Added to watchlist!");
            } else {
                out.print("Already in your watchlist.");
            }
        }
    }
}
