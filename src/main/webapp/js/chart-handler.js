function renderPriceChart(canvasId, labels, prices, coinName) {
  const ctx = document.getElementById(canvasId).getContext("2d");

  new Chart(ctx, {
    type: "line",
    data: {
      labels: labels,
      datasets: [{
        label: coinName + " Price (USD)",
        data: prices,
        borderColor: "#00d4aa",
        backgroundColor: "rgba(0, 212, 170, 0.1)",
        fill: true,
        tension: 0.3,
        pointRadius: 0
      }]
    },
    options: {
      responsive: true,
      plugins: {
        legend: { display: true }
      },
      scales: {
        x: { display: true },
        y: {
          display: true,
          ticks: {
            callback: function (value) {
              return "$" + value.toLocaleString();
            }
          }
        }
      }
    }
  });
}/**
 * 
 */