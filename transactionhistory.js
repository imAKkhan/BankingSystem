document.addEventListener("DOMContentLoaded", function () {
    // Fetch transaction history from the backend
    fetch("/TransactionHistory")
        .then(response => response.json())
        .then(data => {
            const tbody = document.getElementById("TransactionHistory");
            tbody.innerHTML = ""; // Clear any previous data

            data.forEach((transaction, index) => {
                const row = document.createElement("tr");

                row.innerHTML = `
                    <td>${index + 1}</td>
                    <td>${transaction.name}</td>
                    <td>${transaction.sender}</td>
                    <td>${transaction.receiver}</td>
                    <td>₹${transaction.amount}</td>
                    <td>${transaction.date}</td>
                    <td>
                        <a href="editTransaction.html?id=${transaction.id}">Edit</a> |
                        <a href="#" onclick="deleteTransaction(${transaction.id})">Delete</a>
                    </td>
                `;

                tbody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching transaction data:', error);
        });
});

function deleteTransaction(id) {
    if (confirm("Are you sure you want to delete this transaction?")) {
        // Here you would send a request to the backend to delete the transaction.
        console.log("Deleting transaction with ID:", id);
    }
}
