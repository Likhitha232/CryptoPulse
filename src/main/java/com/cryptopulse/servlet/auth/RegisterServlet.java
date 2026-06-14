package com.cryptopulse.servlet.auth;

import com.cryptopulse.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        // Basic validation
        if (name == null || name.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            password == null || password.length() < 6) {

            request.setAttribute("error", "Please fill all fields correctly. Password must be at least 6 characters.");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
            return;
        }

        boolean success = userDAO.registerUser(name.trim(), email.trim().toLowerCase(), password);

        if (success) {
            // Redirect to login with success message
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp?registered=true");
        } else {
            request.setAttribute("error", "Email already registered. Try logging in.");
            request.getRequestDispatcher("/pages/register.jsp").forward(request, response);
        }
    }
}
