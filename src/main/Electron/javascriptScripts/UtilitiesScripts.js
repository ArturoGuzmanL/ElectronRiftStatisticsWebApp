let btn = document.querySelector('#sidebar-button');
let sidebarId = document.querySelector('#interactiveSidebar');
let sidebar = document.querySelector('.sidebar');
let PatchSpan = document.querySelector('#PatchSpan');
let backButton = document.querySelector('#back-button');
let reloadButton = document.querySelector('#reload-button');
let forwardButton = document.querySelector('#forward-button');
let openedOnButton = false;


btn.onclick = function() {
    sidebar.classList.toggle('active');
    if (sidebar.classList.contains('active')) {
        setTimeout(() => {PatchSpan.textContent = "Patch notes"; }, 190);
        openedOnButton = true;
    }else {
        PatchSpan.textContent = "Patch";
        openedOnButton = false;
    }
};

sidebarId.onmouseenter = function() {
    sidebar.classList.add('active');
    setTimeout(() => {PatchSpan.textContent = "Patch notes"; }, 190);
}

sidebarId.onmouseleave = function() {
    if (!openedOnButton) {
        sidebar.classList.remove('active');
        PatchSpan.textContent = "Patch";
    }
}




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

backButton.addEventListener("click", function() {
    history.back();
});

reloadButton.addEventListener("click", function() {
    location.reload();
});

forwardButton.addEventListener("click", function() {
    history.forward();
});

