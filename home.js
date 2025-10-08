// Example: Function to toggle the navigation links visibility on smaller screens (mobile-friendly)
const toggleNavLinks = () => {
    const navLinks = document.querySelector('.page');
    navLinks.classList.toggle('active');
};

// You can add an event listener for this functionality if needed
document.querySelector('.menu-toggle').addEventListener('click', toggleNavLinks);
