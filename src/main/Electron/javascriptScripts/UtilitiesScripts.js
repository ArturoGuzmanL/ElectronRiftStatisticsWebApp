let btn = document.querySelector('#sidebar-button');
let sidebar = document.querySelector('.sidebar');

btn.onclick = function() {
    sidebar.classList.toggle('active');
};

document.querySelector("#show-login").addEventListener("click", function() {
    if (!document.querySelector(".popupSign").classList.contains("active")) {
        document.querySelector(".popup").classList.toggle("active");
    }
});

document.querySelector("#show-signup").addEventListener("click", function() {
    if (!document.querySelector(".popup").classList.contains("active")) {
        document.querySelector(".popupSign").classList.toggle("active");
    }
});

document.querySelector(".popup .close-btn")
    .addEventListener("click", function() {
        document.querySelector(".popup").classList.remove("active");
    });

document.querySelector(".popupSign .close-btn")
    .addEventListener("click", function() {
        document.querySelector(".popupSign").classList.remove("active");
    });