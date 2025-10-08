document.addEventListener('DOMContentLoaded', () => {
    const form = document.querySelector('form');
    const accountInput = document.querySelector('input[name="accountno"]');

    form.addEventListener('submit', (e) => {
        const accountno = accountInput.value.trim();

        // Validate account number length and format
        if (!/^\d{11}$/.test(accountno)) {
            alert("Account number must be exactly 11 digits.");
            e.preventDefault(); // Prevent form submission if validation fails
        }
    });
});
