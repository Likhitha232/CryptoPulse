package com.cryptopulse.servlet.dashboard;

import com.cryptopulse.dao.AlertDAO;
import com.cryptopulse.model.Alert;
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

@WebServlet("/AlertServlet")
public class AlertServlet extends HttpServlet {

    private AlertDAO alertDAO = new AlertDAO();

    // GET: returns list of alerts as JSON [{id, coinId, targetPrice}, ...]
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
        List<Alert> alerts = alertDAO.getAlerts(userId);

        List<Map<String, Object>> result = new ArrayList<>();
        for (Alert a : alerts) {
            Map<String, Object> map = new HashMap<>();
            map.put("id", a.getId());
            map.put("coinId", a.getCoinId());
            map.put("targetPrice", a.getTargetPrice());
            result.add(map);
        }

        Gson gson = new Gson();
        out.print(gson.toJson(result));
    }

    // POST: add or remove an alert
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
            String alertIdStr = request.getParameter("alertId");
            try {
                int alertId = Integer.parseInt(alertIdStr);
                alertDAO.removeAlert(alertId, userId);
                out.print("Alert removed.");
            } catch (NumberFormatException e) {
                out.print("Invalid alert ID.");
            }
            return;
        }

        // Default: add alert
        String coinId = request.getParameter("coinId");
        String targetPriceStr = request.getParameter("targetPrice");

        if (coinId == null || coinId.trim().isEmpty() || targetPriceStr == null) {
            out.print("Invalid input.");
            return;
        }

        try {
            BigDecimal targetPrice = new BigDecimal(targetPriceStr);
            boolean added = alertDAO.addAlert(userId, coinId.trim().toLowerCase(), targetPrice);
            out.print(added ? "Alert added!" : "Failed to add alert.");
        } catch (NumberFormatException e) {
            out.print("Invalid target price.");
        }
    }
}