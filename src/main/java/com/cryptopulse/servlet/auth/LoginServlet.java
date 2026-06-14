package com.cryptopulse.servlet.auth;

import com.cryptopulse.dao.UserDAO;
import com.cryptopulse.dao.LoginHistoryDAO;
import com.cryptopulse.model.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();
    private LoginHistoryDAO loginHistoryDAO = new LoginHistoryDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (email == null || email.trim().isEmpty() || password == null || password.isEmpty()) {
            request.setAttribute("error", "Please enter both email and password.");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
            return;
        }

        User user = userDAO.validateLogin(email.trim().toLowerCase(), password);

        if (user != null) {
            // Create session
            HttpSession session = request.getSession();
            session.setAttribute("userId", user.getId());
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());
            session.setMaxInactiveInterval(30 * 60); // 30 minutes

            // Log this login
            loginHistoryDAO.recordLogin(user.getId());

            // Redirect to dashboard (watchlist as default landing page)
            response.sendRedirect(request.getContextPath() + "/pages/dashboard/watchlist.jsp");
        } else {
            request.setAttribute("error", "Invalid email or password.");
            request.getRequestDispatcher("/pages/login.jsp").forward(request, response);
        }
    }
}