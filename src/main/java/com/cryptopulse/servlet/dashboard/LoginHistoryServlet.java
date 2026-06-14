package com.cryptopulse.servlet.dashboard;

import com.cryptopulse.dao.LoginHistoryDAO;
import com.cryptopulse.model.LoginHistory;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/LoginHistoryServlet")
public class LoginHistoryServlet extends HttpServlet {

    private LoginHistoryDAO loginHistoryDAO = new LoginHistoryDAO();

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
        List<LoginHistory> history = loginHistoryDAO.getLoginHistory(userId);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy, hh:mm a");
        List<Map<String, String>> result = new ArrayList<>();

        for (LoginHistory entry : history) {
            Map<String, String> map = new HashMap<>();
            map.put("loginTime", formatter.format(entry.getLoginTime()));
            result.add(map);
        }

        Gson gson = new GsonBuilder().create();
        out.print(gson.toJson(result));
    }
}