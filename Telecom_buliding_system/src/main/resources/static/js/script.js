document.addEventListener("DOMContentLoaded", function () {
    const preloader = document.getElementById("preloader");
    const mainContent = document.getElementById("main-content");

    // Simulate loading time (e.g., 3 seconds)
    setTimeout(() => {
        preloader.style.opacity = "0"; // Fade out the preloader
        preloader.style.transition = "opacity 1s ease-in-out";
        setTimeout(() => {
            preloader.style.display = "none"; // Hide the preloader
            mainContent.style.display = "block"; // Show the main content
        }, 1000); // Wait for the fade-out animation to complete
    }, 2800); // Adjust the time as needed
});