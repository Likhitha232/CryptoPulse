<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Coin Details - CryptoPulse</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/dark-mode.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.4.0/chart.umd.min.js"></script>
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

        <!-- Coin Header -->
        <div class="card" id="coinHeader" style="display:flex; align-items:center; gap:15px;">
            <p>Loading coin details...</p>
        </div>

        <!-- Stats Grid -->
        <div class="card">
            <h3 style="margin-bottom:10px;">Market Stats</h3>
            <table>
                <tbody id="statsTableBody">
                    <tr><td colspan="2">Loading stats...</td></tr>
                </tbody>
            </table>
        </div>

        <!-- Price Chart -->
        <div class="card">
            <h3 style="margin-bottom:10px;">Price History (7 Days)</h3>
            <canvas id="priceChart" height="100"></canvas>
        </div>

        <!-- Actions -->
        <div class="card" style="text-align:center;">
            <button class="btn" id="watchBtn">+ Add to Watchlist</button>
            <button class="btn" id="alertBtn" style="margin-left:10px; background-color:#ffb347;">🔔 Set Price Alert</button>
        </div>

    </div>

    <script src="${pageContext.request.contextPath}/js/theme-toggle.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script src="${pageContext.request.contextPath}/js/chart-handler.js"></script>
    <script>const CONTEXT_PATH = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/coin-details.js"></script>

</body>
</html>