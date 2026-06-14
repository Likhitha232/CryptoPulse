<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register - CryptoPulse</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dark-mode.css">
</head>
<body>

    <nav class="navbar">
        <div class="logo">CryptoPulse</div>
        <ul>
            <li><a href="index.jsp">Home</a></li>
            <li><a href="market.jsp">Market</a></li>
            <li><a href="login.jsp">Login</a></li>
            <li><a href="register.jsp">Register</a></li>
        </ul>
        <button id="themeToggle" class="theme-toggle">🌙 Dark Mode</button>
    </nav>

    <div class="form-container">
        <h2>Create Account</h2>
        <p style="text-align:center; color:#888; margin-bottom:15px;">Join CryptoPulse for free</p>

        <!-- Error message placeholder -->
        <% if (request.getAttribute("error") != null) { %>
            <p style="color:#e74c3c; text-align:center;"><%= request.getAttribute("error") %></p>
        <% } %>

        <form action="${pageContext.request.contextPath}/RegisterServlet" method="post">
            <input type="text" name="name" placeholder="Full Name" required>
            <input type="email" name="email" placeholder="Email" required>
            <input type="password" name="password" placeholder="Password" required minlength="6">
            <button type="submit" class="btn" style="width:100%; margin-top:10px;">Register</button>
        </form>

        <p style="text-align:center; margin-top:15px;">
            Already have an account? <a href="login.jsp">Login here</a>
        </p>
    </div>

    <script src="${pageContext.request.contextPath}/js/theme-toggle.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>

</body>
</html>