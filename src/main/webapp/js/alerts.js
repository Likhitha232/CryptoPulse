document.addEventListener("DOMContentLoaded", function () {
    loadAlerts();
});

function loadAlerts() {
    const tbody = document.getElementById("alertsBody");

    fetch(CONTEXT_PATH + "/AlertServlet")
        .then(res => res.json())
        .then(alerts => {
            if (!alerts || alerts.length === 0) {
                tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;">No alerts set yet.</td></tr>`;
                return;
            }

            const coinIds = alerts.map(a => a.coinId).join(",");

            fetch(`https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&ids=${coinIds}`)
                .then(res => res.json())
                .then(prices => renderAlerts(alerts, prices));
        })
        .catch(err => {
            tbody.innerHTML = `<tr><td colspan="6" style="text-align:center;color:red;">Failed to load alerts. Please login.</td></tr>`;
        });
}

function renderAlerts(alerts, prices) {
    const tbody = document.getElementById("alertsBody");
    tbody.innerHTML = "";

    alerts.forEach((alert, index) => {
        const coinData = prices.find(p => p.id === alert.coinId);
        const currentPrice = coinData ? coinData.current_price : 0;
        const reached = currentPrice >= alert.targetPrice;

        const row = `
            <tr>
                <td>${index + 1}</td>
                <td>
                    ${coinData ? `<img src="${coinData.image}" width="20" style="vertical-align:middle; margin-right:8px;">` : ""}
                    ${alert.coinId.toUpperCase()}
                </td>
                <td>$${alert.targetPrice.toLocaleString()}</td>
                <td>$${currentPrice.toLocaleString()}</td>
                <td class="${reached ? "gain" : ""}">${reached ? "✅ Target Reached" : "⏳ Watching"}</td>
                <td><button class="btn" style="background-color:#e74c3c; color:#fff;" onclick="removeAlert(${alert.id})">Delete</button></td>
            </tr>
        `;
        tbody.innerHTML += row;
    });
}

function addAlert() {
    const coinId = document.getElementById("coinIdInput").value.trim().toLowerCase();
    const targetPrice = document.getElementById("targetPriceInput").value;

    if (!coinId || !targetPrice || isNaN(targetPrice)) {
        alert("Please enter a valid coin ID and target price.");
        return;
    }

    fetch(CONTEXT_PATH + "/AlertServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `action=add&coinId=${coinId}&targetPrice=${targetPrice}`
    })
    .then(res => res.text())
    .then(() => {
        document.getElementById("coinIdInput").value = "";
        document.getElementById("targetPriceInput").value = "";
        loadAlerts();
    })
    .catch(err => alert("Failed to add alert."));
}

function removeAlert(alertId) {
    fetch(CONTEXT_PATH + "/AlertServlet", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: `action=remove&alertId=${alertId}`
    })
    .then(res => res.text())
    .then(() => loadAlerts())
    .catch(err => alert("Failed to remove alert."));
}/**
 * 
 */