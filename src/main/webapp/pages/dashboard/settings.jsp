<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Settings - CryptoPulse</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dark-mode.css">
</head>
<body>

    <nav class="navbar">
        <div class="logo">CryptoPulse</div>
        <ul>
            <li><a href="${pageContext.request.contextPath}/pages/index.jsp">Home</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/market.jsp">Market</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/dashboard/watchlist.jsp">Watchlist</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/dashboard/alerts.jsp">Alerts</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/dashboard/portfolio.jsp">Portfolio</a></li>
            <li><a href="${pageContext.request.contextPath}/pages/dashboard/settings.jsp">Settings</a></li>
            <li><a href="${pageContext.request.contextPath}/LogoutServlet">Logout</a></li>
        </ul>
        <button id="themeToggle" class="theme-toggle">🌙 Dark Mode</button>
    </nav>

    <div class="container">

        <!-- Success/Error Messages -->
        <% if (request.getAttribute("message") != null) { %>
            <div class="card" style="background-color:#d4f8e8; color:#00b386; text-align:center;">
                <%= request.getAttribute("message") %>
            </div>
        <% } %>
        <% if (request.getAttribute("error") != null) { %>
            <div class="card" style="background-color:#fde2e1; color:#e74c3c; text-align:center;">
                <%= request.getAttribute("error") %>
            </div>
        <% } %>

        <!-- Profile Info -->
        <div class="card">
            <h2 style="margin-bottom:15px;">Profile Information</h2>
            <form action="${pageContext.request.contextPath}/SettingsServlet" method="post">
                <input type="hidden" name="action" value="updateProfile">

                <label for="name">Full Name</label>
                <input type="text" id="name" name="name" value="${sessionScope.userName}" required
                       style="width:100%; padding:10px; margin:8px 0 15px; border:1px solid #ccc; border-radius:5px;">

                <label for="email">Email</label>
                <input type="email" id="email" name="email" value="${sessionScope.userEmail}" required
                       style="width:100%; padding:10px; margin:8px 0 15px; border:1px solid #ccc; border-radius:5px;">

                <button type="submit" class="btn">Save Changes</button>
            </form>
        </div>

        <!-- Change Password -->
        <div class="card">
            <h2 style="margin-bottom:15px;">Change Password</h2>
            <form action="${pageContext.request.contextPath}/SettingsServlet" method="post">
                <input type="hidden" name="action" value="changePassword">

                <label for="currentPassword">Current Password</label>
                <input type="password" id="currentPassword" name="currentPassword" required
                       style="width:100%; padding:10px; margin:8px 0 15px; border:1px solid #ccc; border-radius:5px;">

                <label for="newPassword">New Password</label>
                <input type="password" id="newPassword" name="newPassword" required minlength="6"
                       style="width:100%; padding:10px; margin:8px 0 15px; border:1px solid #ccc; border-radius:5px;">

                <label for="confirmPassword">Confirm New Password</label>
                <input type="password" id="confirmPassword" name="confirmPassword" required minlength="6"
                       style="width:100%; padding:10px; margin:8px 0 15px; border:1px solid #ccc; border-radius:5px;">

                <button type="submit" class="btn">Update Password</button>
            </form>
        </div>

        <!-- Login History -->
        <div class="card">
            <h2 style="margin-bottom:15px;">Recent Login Activity</h2>
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Login Time</th>
                    </tr>
                </thead>
                <tbody id="loginHistoryBody">
                    <tr>
                        <td colspan="2" style="text-align:center;">Loading login history...</td>
                    </tr>
                </tbody>
            </table>
        </div>

        <!-- Danger Zone -->
        <div class="card" style="border:1px solid #e74c3c;">
            <h2 style="margin-bottom:10px; color:#e74c3c;">Danger Zone</h2>
            <p style="color:#888; margin-bottom:15px;">Deleting your account is permanent and cannot be undone.</p>
            <button class="btn" style="background-color:#e74c3c; color:#fff;" onclick="confirmDeleteAccount()">Delete Account</button>
        </div>

    </div>

    <script src="${pageContext.request.contextPath}/js/theme-toggle.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>const CONTEXT_PATH = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/settings.js"></script>

</body>
</html>