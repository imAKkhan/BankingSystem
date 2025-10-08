// Example: Displaying an alert when the user clicks the contact info (for demonstration purposes)
const contactInfo = document.querySelectorAll('p');

contactInfo.forEach(info => {
    info.addEventListener('click', () => {
        alert('Thank you for reaching out! We will get back to you shortly.');
    });
});
