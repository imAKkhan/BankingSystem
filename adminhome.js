// Handle logout functionality
document.querySelector('.logout').addEventListener('click', function(e) {
    e.preventDefault();  // Prevent default action for logout (like redirect)
    logoutUser();
});

function logoutUser() {
    // Clear any session data or localStorage if needed
    localStorage.removeItem('userDetails'); // Example of clearing user data
    window.location.href = 'login.html'; // Redirect to the login page after logging out
}

// Example to display username or other details dynamically
function displayUserInfo() {
    const userDetails = JSON.parse(localStorage.getItem('userDetails')); // Assuming userDetails is stored in localStorage
    if (userDetails) {
        document.getElementById('home').innerHTML = `Welcome, ${userDetails.name}!`;
    } else {
        document.getElementById('home').innerHTML = `Welcome to SBI Account Dashboard!`;
    }
}

// Call displayUserInfo on page load
window.onload = function() {
    displayUserInfo();
};
