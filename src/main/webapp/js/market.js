let allCoins = [];

document.addEventListener("DOMContentLoaded", function () {
    loadMarketData();

    document.getElementById("searchInput").addEventListener("input", applyFilters);
    document.getElementById("sortSelect").addEventListener("change", applyFilters);
});

function loadMarketData() {
    const tbody = document.getElementById("marketTableBody");

    fetch("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=50&page=1")
        .then(response => response.json())
        .then(data => {
            allCoins = data;
            renderTable(allCoins);
        })
        .catch(err => {
            tbody.innerHTML = `<tr><td colspan="8" style="text-align:center;color:red;">Failed to load market data.</td></tr>`;
        });
}

function applyFilters() {
    const searchTerm = document.getElementById("searchInput").value.toLowerCase();
    const sortOption = document.getElementById("sortSelect").value;

    let filtered = allCoins.filter(coin =>
        coin.name.toLowerCase().includes(searchTerm) ||
        coin.symbol.toLowerCase().includes(searchTerm)
    );

    if (sortOption === "gainers") {
        filtered.sort((a, b) => b.price_change_percentage_24h - a.price_change_percentage_24h);
    } else if (sortOption === "losers") {
        filtered.sort((a, b) => a.price_change_percentage_24h - b.price_change_percentage_24h);
    } else if (sortOption === "price_desc") {
        filtered.sort((a, b) => b.current_price - a.current_price);
    } else {
        filtered.sort((a, b) => b.market_cap - a.market_cap);
    }

    renderTable(filtered);
}

function renderTable(coins) {
    const tbody = document.getElementById("marketTableBody");
    tbody.innerHTML = "";

    if (coins.length === 0) {
        tbody.innerHTML = `<tr><td colspan="8" style="text-align:center;">No coins found.</td></tr>`;
        return;
    }

    coins.forEach((coin, index) => {
        const changeClass = coin.price_change_percentage_24h >= 0 ? "gain" : "loss";
        const changeSign = coin.price_change_percentage_24h >= 0 ? "+" : "";

        const row = `
            <tr>
                <td>${index + 1}</td>
                <td>
                    <a href="${CONTEXT_PATH}/pages/coin-details.jsp?id=${coin.id}" style="text-decoration:none; color:inherit;">
                        <img src="${coin.image}" alt="${coin.name}" width="20" style="vertical-align:middle; margin-right:8px;">
                        ${coin.name} (${coin.symbol.toUpperCase()})
                    </a>
                </td>
                <td>$${coin.current_price.toLocaleString()}</td>
                <td class="${changeClass}">${changeSign}${coin.price_change_percentage_24h.toFixed(2)}%</td>
                <td>$${coin.market_cap.toLocaleString()}</td>
                <td>$${coin.total_volume.toLocaleString()}</td>
                <td>${coin.circulating_supply.toLocaleString()} ${coin.symbol.toUpperCase()}</td>
                <td><button class="btn" onclick="addToWatchlist('${coin.id}')">+ Watch</button></td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

function addToWatchlist(coinId) {
    fetch(CONTEXT_PATH + "/WatchlistServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "coinId=" + coinId
    })
    .then(response => response.text())
    .then(msg => alert(msg))
    .catch(err => alert("Please login to add to watchlist."));
}