<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Price Alerts - CryptoPulse</title>
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

        <div class="card">
            <h2 style="margin-bottom:15px;">Price Alerts</h2>
            <p style="color:#888; margin-bottom:15px;">Get notified when a coin hits your target price.</p>

            <!-- Add New Alert Form -->
            <div style="display:flex; gap:10px; margin-bottom:20px; flex-wrap:wrap;">
                <input type="text" id="coinIdInput" placeholder="Coin ID (e.g. bitcoin)"
                       style="flex:1; padding:10px; border-radius:5px; border:1px solid #ccc;">
                <input type="number" id="targetPriceInput" placeholder="Target Price (USD)"
                       style="flex:1; padding:10px; border-radius:5px; border:1px solid #ccc;">
                <button class="btn" onclick="addAlert()">+ Add Alert</button>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Coin</th>
                        <th>Target Price</th>
                        <th>Current Price</th>
                        <th>Status</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="alertsBody">
                    <tr>
                        <td colspan="6" style="text-align:center;">Loading alerts...</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>

    <script src="${pageContext.request.contextPath}/js/theme-toggle.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>const CONTEXT_PATH = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/alerts.js"></script>

</body>
</html>