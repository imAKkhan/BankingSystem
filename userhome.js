document.addEventListener("DOMContentLoaded", function () {
    const navLinks = document.querySelectorAll(".navigation a");

    // Highlight active menu item based on URL
    navLinks.forEach(link => {
        if (window.location.href.includes(link.getAttribute("href"))) {
            link.style.color = "#ffcc00";
            link.style.textDecoration = "underline";
        }
    });

    // Greeting Message based on Time of Day
    const container = document.querySelector(".container");
    const time = new Date().getHours();
    let greeting;

    if (time < 12) {
        greeting = "Good Morning!";
    } else if (time < 18) {
        greeting = "Good Afternoon!";
    } else {
        greeting = "Good Evening!";
    }

    container.innerHTML += `<p><strong>${greeting}</strong></p>`;
});
