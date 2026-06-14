package com.cryptopulse.servlet.dashboard;

import com.cryptopulse.dao.UserDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/SettingsServlet")
public class SettingsServlet extends HttpServlet {

    private UserDAO userDAO = new UserDAO();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("userId") == null) {
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp");
            return;
        }

        int userId = (int) session.getAttribute("userId");
        String action = request.getParameter("action");

        switch (action) {
            case "updateProfile":
                handleUpdateProfile(request, response, session, userId);
                break;
            case "changePassword":
                handleChangePassword(request, response, session, userId);
                break;
            case "deleteAccount":
                handleDeleteAccount(request, response, session, userId);
                break;
            default:
                response.sendRedirect(request.getContextPath() + "/pages/dashboard/settings.jsp");
        }
    }

    private void handleUpdateProfile(HttpServletRequest request, HttpServletResponse response,
                                       HttpSession session, int userId)
            throws ServletException, IOException {

        String name = request.getParameter("name");
        String email = request.getParameter("email");

        if (name == null || name.trim().isEmpty() || email == null || email.trim().isEmpty()) {
            request.setAttribute("error", "Name and email cannot be empty.");
        } else {
            boolean success = userDAO.updateProfile(userId, name.trim(), email.trim().toLowerCase());
            if (success) {
                session.setAttribute("userName", name.trim());
                session.setAttribute("userEmail", email.trim().toLowerCase());
                request.setAttribute("message", "Profile updated successfully.");
            } else {
                request.setAttribute("error", "Failed to update profile.");
            }
        }

        request.getRequestDispatcher("/pages/dashboard/settings.jsp").forward(request, response);
    }

    private void handleChangePassword(HttpServletRequest request, HttpServletResponse response,
                                        HttpSession session, int userId)
            throws ServletException, IOException {

        String currentPassword = request.getParameter("currentPassword");
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");

        if (currentPassword == null || newPassword == null || confirmPassword == null ||
            newPassword.length() < 6) {
            request.setAttribute("error", "Please fill all password fields. New password must be at least 6 characters.");
        } else if (!newPassword.equals(confirmPassword)) {
            request.setAttribute("error", "New passwords do not match.");
        } else {
            boolean success = userDAO.changePassword(userId, currentPassword, newPassword);
            if (success) {
                request.setAttribute("message", "Password updated successfully.");
            } else {
                request.setAttribute("error", "Current password is incorrect.");
            }
        }

        request.getRequestDispatcher("/pages/dashboard/settings.jsp").forward(request, response);
    }

    private void handleDeleteAccount(HttpServletRequest request, HttpServletResponse response,
                                       HttpSession session, int userId)
            throws IOException {

        boolean success = userDAO.deleteUser(userId);

        if (success) {
            session.invalidate();
            response.sendRedirect(request.getContextPath() + "/pages/login.jsp?deleted=true");
        } else {
            response.sendRedirect(request.getContextPath() + "/pages/dashboard/settings.jsp?error=delete_failed");
        }
    }
}
