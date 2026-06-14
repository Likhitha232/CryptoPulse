document.addEventListener("DOMContentLoaded", function () {
    const params = new URLSearchParams(window.location.search);
    const coinId = params.get("id");

    if (!coinId) {
        document.getElementById("coinHeader").innerHTML = "<p>No coin selected.</p>";
        return;
    }

    loadCoinDetails(coinId);
    loadPriceHistory(coinId);

    document.getElementById("watchBtn").addEventListener("click", () => addToWatchlist(coinId));
    document.getElementById("alertBtn").addEventListener("click", () => setPriceAlert(coinId));
});

function loadCoinDetails(coinId) {
    fetch(`https://api.coingecko.com/api/v3/coins/${coinId}?localization=false&tickers=false&community_data=false&developer_data=false`)
        .then(res => res.json())
        .then(data => {
            const market = data.market_data;
            const changeClass = market.price_change_percentage_24h >= 0 ? "gain" : "loss";
            const changeSign = market.price_change_percentage_24h >= 0 ? "+" : "";

            document.getElementById("coinHeader").innerHTML = `
                <img src="${data.image.large}" alt="${data.name}" width="50">
                <div>
                    <h2>${data.name} (${data.symbol.toUpperCase()})</h2>
                    <p style="font-size:22px; font-weight:bold;">$${market.current_price.usd.toLocaleString()}
                        <span class="${changeClass}" style="font-size:16px; margin-left:8px;">
                            ${changeSign}${market.price_change_percentage_24h.toFixed(2)}%
                        </span>
                    </p>
                </div>
            `;

            document.getElementById("statsTableBody").innerHTML = `
                <tr><td><strong>Market Cap</strong></td><td>$${market.market_cap.usd.toLocaleString()}</td></tr>
                <tr><td><strong>24h Trading Volume</strong></td><td>$${market.total_volume.usd.toLocaleString()}</td></tr>
                <tr><td><strong>Circulating Supply</strong></td><td>${market.circulating_supply.toLocaleString()} ${data.symbol.toUpperCase()}</td></tr>
                <tr><td><strong>Total Supply</strong></td><td>${market.total_supply ? market.total_supply.toLocaleString() : "N/A"}</td></tr>
                <tr><td><strong>All-Time High</strong></td><td>$${market.ath.usd.toLocaleString()}</td></tr>
                <tr><td><strong>All-Time Low</strong></td><td>$${market.atl.usd.toLocaleString()}</td></tr>
            `;
        })
        .catch(err => {
            document.getElementById("coinHeader").innerHTML = "<p style='color:red;'>Failed to load coin details.</p>";
        });
}

function loadPriceHistory(coinId) {
    fetch(`https://api.coingecko.com/api/v3/coins/${coinId}/market_chart?vs_currency=usd&days=7`)
        .then(res => res.json())
        .then(data => {
            const labels = data.prices.map(p => {
                const date = new Date(p[0]);
                return date.toLocaleDateString('en-US', { month: 'short', day: 'numeric' });
            });
            const prices = data.prices.map(p => p[1]);

            renderPriceChart("priceChart", labels, prices, coinId.toUpperCase());
        })
        .catch(err => {
            console.error("Failed to load chart data", err);
        });
}

function addToWatchlist(coinId) {
    fetch(CONTEXT_PATH + "/WatchlistServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "coinId=" + coinId
    })
    .then(res => res.text())
    .then(msg => alert(msg))
    .catch(err => alert("Please login to add to watchlist."));
}

function setPriceAlert(coinId) {
    const targetPrice = prompt("Enter target price (USD) for alert:");
    if (!targetPrice || isNaN(targetPrice)) return;

    fetch(CONTEXT_PATH + "/AlertServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `coinId=${coinId}&targetPrice=${targetPrice}`
    })
    .then(res => res.text())
    .then(msg => alert(msg))
    .catch(err => alert("Please login to set an alert."));
}