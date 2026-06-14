<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Watchlist - CryptoPulse</title>
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
            <h2 style="margin-bottom:15px;">My Watchlist</h2>
            <p style="color:#888; margin-bottom:15px;">Coins you're tracking, with live prices.</p>

            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Coin</th>
                        <th>Price</th>
                        <th>24h Change</th>
                        <th>Market Cap</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="watchlistBody">
                    <tr>
                        <td colspan="6" style="text-align:center;">Loading watchlist...</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>
	
    <script src="${pageContext.request.contextPath}/js/theme-toggle.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>const CONTEXT_PATH = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/watchlist.js"></script>

</body>
</html>