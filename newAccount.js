// newAccount.js

document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('createAccountForm');

    form.addEventListener('submit', (e) => {
        e.preventDefault(); // Prevent form submission for validation
        if (validateForm()) {
            form.submit(); // Submit the form if validation passes
        }
    });
});

function validateForm() {
    let isValid = true;

    const username = document.querySelector('input[name="username"]');
    const aadharno = document.querySelector('input[name="aadharno"]');
    const emailname = document.querySelector('input[name="emailname"]');
    const mobileno = document.querySelector('input[name="mobileno"]');
    const fathername = document.querySelector('input[name="fathername"]');
    const balanceno = document.querySelector('input[name="balanceno"]');
    const accounttype = document.querySelector('select[name="accounttype"]');
    const gendername = document.querySelector('select[name="gendername"]');

    // Clear previous errors
    document.querySelectorAll('.error-message').forEach(el => el.remove());

    // Username Validation
    if (username.value.trim() === '') {
        showError(username, 'Username is required.');
        isValid = false;
    }

    // Aadhar Validation (Assuming Aadhar is a 12-digit number in India)
    if (!/^\d{12}$/.test(aadharno.value.trim())) {
        showError(aadharno, 'Aadhar No must be a 12-digit number.');
        isValid = false;
    }

    // Email Validation
    if (!/^\S+@\S+\.\S+$/.test(emailname.value.trim())) {
        showError(emailname, 'Please enter a valid email address.');
        isValid = false;
    }

    // Phone Number Validation (10-digit number)
    if (!/^\d{10}$/.test(mobileno.value.trim())) {
        showError(mobileno, 'Phone Number must be a 10-digit number.');
        isValid = false;
    }

    // Father's Name Validation
    if (fathername.value.trim() === '') {
        showError(fathername, 'Father Name is required.');
        isValid = false;
    }

    // Balance Validation (Ensure it's a positive number)
    if (!/^\d+(\.\d{1,2})?$/.test(balanceno.value.trim()) || parseFloat(balanceno.value) <= 0) {
        showError(balanceno, 'Submit Balance must be a positive number.');
        isValid = false;
    }

    // Account Type Validation
    if (accounttype.value === '') {
        showError(accounttype, 'Please select an Account Type.');
        isValid = false;
    }

    // Gender Validation
    if (gendername.value === '') {
        showError(gendername, 'Please select a Gender.');
        isValid = false;
    }

    return isValid;
}

// Function to show error messages
function showError(input, message) {
    const error = document.createElement('div');
    error.className = 'error-message';
    error.style.color = 'red';
    error.style.marginTop = '5px';
    error.textContent = message;
    input.parentElement.appendChild(error);
    input.focus();
}
