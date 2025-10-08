document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const username = document.querySelector('#username');
    const password = document.querySelector('#password');
    const email = document.querySelector('#email');
    const contact = document.querySelector('#contact');
    
    form.addEventListener('submit', function(event) {
        // Clear any previous error messages
        let errorMessages = [];
        
        // Username validation
        if (username.value.trim() === "") {
            errorMessages.push("Username is required.");
        }
        
        // Password validation (simple example)
        if (password.value.length < 6) {
            errorMessages.push("Password must be at least 6 characters.");
        }

        // Email validation
        if (!isValidEmail(email.value)) {
            errorMessages.push("Please enter a valid email address.");
        }

        // Contact validation (10-digit phone number)
        if (!isValidContact(contact.value)) {
            errorMessages.push("Please enter a valid 10-digit contact number.");
        }

        // If there are any error messages, prevent form submission and display errors
        if (errorMessages.length > 0) {
            event.preventDefault(); // Stop form submission
            alert(errorMessages.join("\n")); // Display all error messages
        }
    });

    // Simple email validation function
    function isValidEmail(email) {
        const emailRegex = /^[a-zA-Z0-9_+&*-]+(?:\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\.)+[a-zA-Z]{2,7}$/;
        return emailRegex.test(email);
    }

    // Simple contact number validation function (10 digits)
    function isValidContact(contact) {
        const contactRegex = /^[0-9]{10}$/;
        return contactRegex.test(contact);
    }
});
