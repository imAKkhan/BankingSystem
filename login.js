// JavaScript to handle form submission or validation
const loginForm = document.querySelector('form');
const usernameInput = document.getElementById('username');
const passwordInput = document.getElementById('password');
const roleInput = document.getElementById('role');

loginForm.addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission

    const username = usernameInput.value;
    const password = passwordInput.value;
    const role = roleInput.value;

    // Simple validation (check if fields are not empty)
    if (username && password && role) {
        // Here you could add AJAX for a real login check (optional)
        alert(`Logging in as ${role}...`);
        // Submit the form if everything is valid
        loginForm.submit();
    } else {
        alert("Please fill in all fields.");
    }
});
