document.getElementById("changePasswordForm").addEventListener("submit", function (event) {
    event.preventDefault(); // Prevent form from submitting immediately

    const currentPassword = document.getElementById("currentPassword").value;
    const newPassword = document.getElementById("newPassword").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    const errorMessage = document.getElementById("error-message");

    // Password validation rules
    if (newPassword.length < 8) {
        errorMessage.textContent = "New password must be at least 8 characters long.";
        return;
    }
    if (!/[A-Z]/.test(newPassword) || !/[a-z]/.test(newPassword) || !/\d/.test(newPassword) || !/[@$!%*?&]/.test(newPassword)) {
        errorMessage.textContent = "Password must include uppercase, lowercase, number, and special character.";
        return;
    }
    if (newPassword !== confirmPassword) {
        errorMessage.textContent = "New password and confirm password do not match.";
        return;
    }

    // If all checks pass, submit the form
    this.submit();
});
