document.addEventListener("DOMContentLoaded", function () {
  const currentPage = window.location.pathname.split("/").pop();
  const navLinks = document.querySelectorAll(".navbar ul li a");

  navLinks.forEach(link => {
    if (link.getAttribute("href") === currentPage) {
      link.style.color = "#00d4aa";
      link.style.fontWeight = "bold";
    }
  });
});/**
 * 
 */