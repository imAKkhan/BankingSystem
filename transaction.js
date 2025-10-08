document.addEventListener("DOMContentLoaded", function () {
    fetch("TransactionHistoryServlet")
        .then(response => response.json())
        .then(data => {
            let tableBody = document.querySelector("#transactionTable tbody");
            tableBody.innerHTML = ""; // Clear previous data

            data.forEach(transaction => {
                let row = `<tr>
                    <td>${transaction.transaction_id}</td>
                    <td>${transaction.account_no}</td>
                    <td>₹${transaction.amount}</td>
                    <td>${transaction.transaction_date}</td>
                    <td>${transaction.status}</td>
                </tr>`;
                tableBody.innerHTML += row;
            });
        })
        .catch(error => console.error("Error fetching transaction history:", error));
});
