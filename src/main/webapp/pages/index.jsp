<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>CryptoPulse - Real-Time Crypto Market Tracker</title>
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

    <div class="container">

        <!-- Hero Section -->
        <div class="card" style="text-align:center; padding:50px;">
            <h1>Track Crypto Markets in Real Time</h1>
            <p style="margin:15px 0; color:#888;">
                Live prices, market trends, watchlists, and alerts — all in one place.
            </p>
            <a href="market.jsp" class="btn">View Market</a>
        </div>

        <!-- Top Coins Preview -->
        <div class="card">
            <h2 style="margin-bottom:15px;">Top Coins</h2>
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Coin</th>
                        <th>Price</th>
                        <th>24h Change</th>
                        <th>Market Cap</th>
                    </tr>
                </thead>
                <tbody id="topCoinsBody">
                    <!-- Filled dynamically via JS/API -->
                    <tr>
                        <td colspan="5" style="text-align:center;">Loading top coins...</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>

    <script src="${pageContext.request.contextPath}/js/theme-toggle.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>const CONTEXT_PATH = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/home.js"></script>

</body>
</html>