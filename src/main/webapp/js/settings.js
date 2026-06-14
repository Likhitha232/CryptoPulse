document.addEventListener("DOMContentLoaded", function () {
    loadLoginHistory();
});

function loadLoginHistory() {
    const tbody = document.getElementById("loginHistoryBody");

    fetch(CONTEXT_PATH + "/LoginHistoryServlet")
        .then(res => res.json())
        .then(history => {
            if (!history || history.length === 0) {
                tbody.innerHTML = `<tr><td colspan="2" style="text-align:center;">No login history found.</td></tr>`;
                return;
            }

            tbody.innerHTML = "";
            history.forEach((entry, index) => {
                tbody.innerHTML += `
                    <tr>
                        <td>${index + 1}</td>
                        <td>${entry.loginTime}</td>
                    </tr>
                `;
            });
        })
        .catch(err => {
            tbody.innerHTML = `<tr><td colspan="2" style="text-align:center;color:red;">Failed to load login history.</td></tr>`;
        });
}

function confirmDeleteAccount() {
    if (confirm("Are you sure you want to permanently delete your account? This cannot be undone.")) {
        fetch(CONTEXT_PATH + "/SettingsServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "action=deleteAccount"
        })
        .then(res => {
            if (res.redirected) {
                window.location.href = res.url;
            } else {
                return res.text().then(msg => alert(msg));
            }
        })
        .catch(err => alert("Failed to delete account."));
    }
}