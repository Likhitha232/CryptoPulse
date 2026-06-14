<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Market Overview - CryptoPulse</title>
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

        <div class="card">
            <h2 style="margin-bottom:15px;">Market Overview</h2>

            <!-- Search & Filter Controls -->
            <div style="display:flex; gap:10px; margin-bottom:15px; flex-wrap:wrap;">
                <input type="text" id="searchInput" placeholder="Search coin (e.g. Bitcoin, ETH)"
                       style="flex:1; padding:10px; border-radius:5px; border:1px solid #ccc;">
                <select id="sortSelect" style="padding:10px; border-radius:5px; border:1px solid #ccc;">
                    <option value="market_cap_desc">Market Cap (High to Low)</option>
                    <option value="gainers">Top Gainers</option>
                    <option value="losers">Top Losers</option>
                    <option value="price_desc">Price (High to Low)</option>
                </select>
            </div>

            <table>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>Coin</th>
                        <th>Price</th>
                        <th>24h Change</th>
                        <th>Market Cap</th>
                        <th>Volume (24h)</th>
                        <th>Circulating Supply</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody id="marketTableBody">
                    <tr>
                        <td colspan="8" style="text-align:center;">Loading market data...</td>
                    </tr>
                </tbody>
            </table>
        </div>

    </div>

    <script src="${pageContext.request.contextPath}/js/theme-toggle.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>const CONTEXT_PATH = "${pageContext.request.contextPath}";</script>
    <script src="${pageContext.request.contextPath}/js/market.js"></script>

</body>
</html>