document.addEventListener("DOMContentLoaded", function () {
    const tbody = document.getElementById("topCoinsBody");

    fetch("https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=5&page=1")
        .then(response => response.json())
        .then(data => {
            tbody.innerHTML = "";
            data.forEach((coin, index) => {
                const changeClass = coin.price_change_percentage_24h >= 0 ? "gain" : "loss";
                const changeSign = coin.price_change_percentage_24h >= 0 ? "+" : "";

                const row = `
                    <tr>
                        <td>${index + 1}</td>
                        <td>
                            <img src="${coin.image}" alt="${coin.name}" width="20" style="vertical-align:middle; margin-right:8px;">
                            ${coin.name} (${coin.symbol.toUpperCase()})
                        </td>
                        <td>$${coin.current_price.toLocaleString()}</td>
                        <td class="${changeClass}">${changeSign}${coin.price_change_percentage_24h.toFixed(2)}%</td>
                        <td>$${coin.market_cap.toLocaleString()}</td>
                    </tr>
                `;
                tbody.innerHTML += row;
            });
        })
        .catch(err => {
            tbody.innerHTML = `<tr><td colspan="5" style="text-align:center;color:red;">Failed to load data.</td></tr>`;
        });
});/**
 * 
 */