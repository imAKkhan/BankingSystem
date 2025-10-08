function fetchCreditHistory() {
    let accountNo = document.getElementById("accountNo").value;
    
    if (!accountNo.match(/^\d{11}$/)) {
        alert("Please enter a valid 11-digit account number.");
        return;
    }

    let xhr = new XMLHttpRequest();
    xhr.open("GET", "CreditHistoryServlet?accountno=" + accountNo, true);
    xhr.setRequestHeader("Content-Type", "application/xml");

    xhr.onload = function () {
        if (xhr.status === 200) {
            let xmlData = xhr.responseXML;
            let tableBody = document.querySelector("#creditList tbody");
            tableBody.innerHTML = ""; // Clear previous data

            if (!xmlData) {
                tableBody.innerHTML = "<tr><td colspan='4'>Invalid XML response.</td></tr>";
                return;
            }

            let transactions = xmlData.getElementsByTagName("transaction");

            if (transactions.length === 0) {
                tableBody.innerHTML = "<tr><td colspan='4'>No credit history found.</td></tr>";
                return;
            }

            for (let i = 0; i < transactions.length; i++) {
                let id = transactions[i].getElementsByTagName("id")[0].textContent;
                let username = transactions[i].getElementsByTagName("username")[0].textContent;
                let amount = transactions[i].getElementsByTagName("amount")[0].textContent;
                let date = transactions[i].getElementsByTagName("date")[0].textContent;

                let row = `<tr>
                            <td>${id}</td>
                            <td>${username}</td>
                            <td>${amount}</td>
                            <td>${date}</td>
                           </tr>`;
                tableBody.innerHTML += row;
            }
        } else {
            alert("Failed to fetch credit history.");
        }
    };

    xhr.onerror = function () {
        alert("Request error. Check server.");
    };

    xhr.send();
}
