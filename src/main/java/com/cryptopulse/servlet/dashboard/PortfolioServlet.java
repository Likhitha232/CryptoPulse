package com.cryptopulse.servlet.dashboard;

import com.cryptopulse.dao.PortfolioDAO;
import com.cryptopulse.model.PortfolioItem;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/PortfolioServlet")
public class PortfolioServlet extends HttpServlet {

    private PortfolioDAO portfolioDAO = new PortfolioDAO();

    // GET: returns list of holdings as JSON [{id, coinId, quantity}, ...]
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
        List<PortfolioItem> items = portfolioDAO.getPortfolio(userId);

        List<Map<String, Object>> result = new ArrayList<>();
        for (PortfolioItem item : items) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", item.getId());
            map.put("coinId", item.getCoinId());
            map.put("quantity", item.getQuantity());
            result.add(map);
        }

        Gson gson = new Gson();
        out.print(gson.toJson(result));
    }

    // POST: add or remove a holding
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

        if ("remove".equals(action)) {
            String holdingIdStr = request.getParameter("holdingId");
            try {
                int holdingId = Integer.parseInt(holdingIdStr);
                portfolioDAO.removeHolding(holdingId, userId);
                out.print("Holding removed.");
            } catch (NumberFormatException e) {
                out.print("Invalid holding ID.");
            }
            return;
        }

        // Default: add holding
        String coinId = request.getParameter("coinId");
        String quantityStr = request.getParameter("quantity");

        if (coinId == null || coinId.trim().isEmpty() || quantityStr == null) {
            out.print("Invalid input.");
            return;
        }

        try {
            BigDecimal quantity = new BigDecimal(quantityStr);
            if (quantity.compareTo(BigDecimal.ZERO) <= 0) {
                out.print("Quantity must be positive.");
                return;
            }
            boolean added = portfolioDAO.addHolding(userId, coinId.trim().toLowerCase(), quantity);
            out.print(added ? "Holding added!" : "Failed to add holding.");
        } catch (NumberFormatException e) {
            out.print("Invalid quantity.");
        }
    }
}