document.addEventListener("DOMContentLoaded", function () {
    loadPortfolio();
});

function loadPortfolio() {
    const tbody = document.getElementById("holdingsBody");

    fetch(CONTEXT_PATH + "/PortfolioServlet")
        .then(res => res.json())
        .then(holdings => {
            if (!holdings || holdings.length === 0) {
                tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;">No holdings yet. Add a coin above.</td></tr>`;
                document.getElementById("totalValue").textContent = "$0.00";
                return;
            }

            const coinIds = holdings.map(h => h.coinId).join(",");

            fetch(`https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=${coinIds}`)
                .then(res => res.json())
                .then(prices => renderPortfolio(holdings, prices));
        })
        .catch(err => {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;color:red;">Failed to load portfolio. Please login.</td></tr>`;
        });
}

function renderPortfolio(holdings, prices) {
    const tbody = document.getElementById("holdingsBody");
    tbody.innerHTML = "";

    let totalValue = 0;

    holdings.forEach((holding, index) => {
        const coinData = prices.find(p => p.id === holding.coinId);
        const currentPrice = coinData ? coinData.current_price : 0;
        const value = currentPrice * holding.quantity;
        totalValue += value;

        const row = `
            <tr>
                <td>${index + 1}</td>
                <td>
                    ${coinData ? `<img src="${coinData.image}" width="20" style="vertical-align:middle; margin-right:8px;">` : ""}
                    ${holding.coinId.toUpperCase()}
                </td>
                <td>${holding.quantity}</td>
                <td>$${currentPrice.toLocaleString()}</td>
                <td>$${value.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2})}</td>
                <td><button class="btn" style="background-color:#e74c3c; color:#fff;" onclick="removeHolding(${holding.id})">Remove</button></td>
            </tr>
        `;
        tbody.innerHTML += row;
    });

    document.getElementById("totalValue").textContent =
        "$" + totalValue.toLocaleString(undefined, {minimumFractionDigits: 2, maximumFractionDigits: 2});
}

function addHolding() {
    const coinId = document.getElementById("coinIdInput").value.trim().toLowerCase();
    const quantity = document.getElementById("quantityInput").value;

    if (!coinId || !quantity || isNaN(quantity) || quantity <= 0) {
        alert("Please enter a valid coin ID and quantity.");
        return;
    }

    fetch(CONTEXT_PATH + "/PortfolioServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `action=add&coinId=${coinId}&quantity=${quantity}`
    })
    .then(res => res.text())
    .then(() => {
        document.getElementById("coinIdInput").value = "";
        document.getElementById("quantityInput").value = "";
        loadPortfolio();
    })
    .catch(err => alert("Failed to add holding."));
}

function removeHolding(holdingId) {
    fetch(CONTEXT_PATH + "/PortfolioServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `action=remove&holdingId=${holdingId}`
    })
    .then(res => res.text())
    .then(() => loadPortfolio())
    .catch(err => alert("Failed to remove holding."));
}