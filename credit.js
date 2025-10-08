document.addEventListener("DOMContentLoaded", function() {
    const form = document.querySelector("form");
    const accountnoInput = document.querySelector("input[name='accountno']");
    const amountInput = document.querySelector("input[name='amount']");
    
    form.addEventListener("submit", function(event) {
        let accountno = accountnoInput.value.trim();
        let amount = amountInput.value.trim();

        // Validate account number
        if (accountno === "") {
            alert("Account number is required.");
            event.preventDefault(); // Prevent form submission
            return;
        }

        // Validate amount
        if (amount === "" || isNaN(amount) || parseFloat(amount) <= 0) {
            alert("Please enter a valid amount greater than zero.");
            event.preventDefault(); // Prevent form submission
            return;
        }

        // You can add any additional validation here if needed

        // If all validation passed, let the form submit
    });
});
