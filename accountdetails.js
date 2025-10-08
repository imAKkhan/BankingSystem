document.addEventListener("DOMContentLoaded", function () {
    fetchAccountDetails();
});

function fetchAccountDetails() {
    fetch("AccountDetails") // Ensure this matches your servlet mapping in `web.xml`
        .then(response => response.text()) // Expecting HTML from servlet
        .then(data => {
            document.getElementById("accountDetailsBody").innerHTML = data;
        })
        .catch(error => {
            console.error("Error fetching account details:", error);
            document.getElementById("accountDetailsBody").innerHTML = "<tr><td colspan='9' style='color:red;'>Failed to load data</td></tr>";
        });
}
