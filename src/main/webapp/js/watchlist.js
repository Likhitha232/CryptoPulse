document.addEventListener("DOMContentLoaded", function () {
    loadWatchlist();
});

function loadWatchlist() {
    const tbody = document.getElementById("watchlistBody");

    fetch(CONTEXT_PATH + "/WatchlistServlet")
        .then(res => res.json())
        .then(coinIds => {
            if (!coinIds || coinIds.length === 0) {
                tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;">Your watchlist is empty. Add coins from the Market page.</td></tr>`;
                return;
            }

            const idsParam = coinIds.join(",");
            return fetch(`https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=${idsParam}`)
                .then(res => res.json())
                .then(coins => renderWatchlist(coins));
        })
        .catch(err => {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;color:red;">Failed to load watchlist. Please login.</td></tr>`;
        });
}

function renderWatchlist(coins) {
    const tbody = document.getElementById("watchlistBody");
    tbody.innerHTML = "";

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
                <td><button class="btn" style="background-color:#e74c3c; color:#fff;" onclick="removeFromWatchlist('${coin.id}')">Remove</button></td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

function removeFromWatchlist(coinId) {
    fetch(CONTEXT_PATH + "/WatchlistServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `action=remove&coinId=${coinId}`
    })
    .then(res => res.text())
    .then(() => loadWatchlist())
    .catch(err => alert("Failed to remove coin."));
}