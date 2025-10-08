function validateForm() {
    const fromAccount = document.querySelector('input[name="fromAccount"]').value;
    const toAccount = document.querySelector('input[name="toAccount"]').value;
    const amount = document.querySelector('input[name="amount"]').value;

    if (fromAccount.length !== 11 || toAccount.length !== 11) {
        alert("Account numbers must be exactly 11 digits.");
        return false;
    }

    if (fromAccount === toAccount) {
        alert("From Account and To Account cannot be the same.");
        return false;
    }

    if (amount <= 0) {
        alert("Amount must be a positive number.");
        return false;
    }

    return true;
}
