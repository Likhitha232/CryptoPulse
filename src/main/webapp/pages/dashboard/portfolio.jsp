<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Portfolio - CryptoPulse</title>
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

        <!-- Summary Card -->
        <div class="card" style="text-align:center;">
            <h2>Portfolio Value</h2>
            <p style="font-size:32px; font-weight:bold; margin:10px 0;" id="totalValue">$0.00</p>
            <p style="color:#888;">Total estimated value of your holdings</p>
        </div>

        <!-- Add Holding Form -->
        <div class="card">
            <h3 style="margin-bottom:10px;">Add Holding</h3>
            <div style="display:flex; gap:10px; flex-wrap:wrap;">
                <input type="text" id="coinIdInput" placeholder="Coin ID (e.g. bitcoin)"
                       style="flex:1; padding:10px; border-radius:5px; border:1px solid #ccc;">
                <input type="number" id="quantityInput" placeholder="Quantity" step="any"
                       style="flex:1; padding:10px; border-radius:5px; border:1px solid #ccc;">
                <button class="btn" onclick="addHolding()">+ Add</button>
            </div>
        </div>

        <!-- Holdings Table -->
        <div class="card">
            <h3 style="margin-bottom:15px;">My Holdings</h3>
            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Coin</th>
                        <th>Quantity</th>
                        <th>Current Price</th>
                        <th>Value</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="holdingsBody">
                    <tr>
                        <td colspan="6" style="text-align:center;">Loading portfolio...</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>

    <script src="${pageContext.request.contextPath}/js/theme-toggle.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>const CONTEXT_PATH = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/portfolio.js"></script>

</body>
</html>