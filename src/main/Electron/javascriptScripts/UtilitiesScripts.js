let btn = document.querySelector('#sidebar-button');
let sidebarId = document.querySelector('#interactiveSidebar');
let sidebar = document.querySelector('.sidebar');
let PatchSpan = document.querySelector('#PatchSpan');
let backButton = document.querySelector('#back-button');
let reloadButton = document.querySelector('#reload-button');
let forwardButton = document.querySelector('#forward-button');
let openedOnButton = false;
let logPassShow = document.querySelector('#logPassShow');
let logPassField = document.querySelector('#logPassword');
let signPassField = document.querySelector("#sigPassword");
let signPassShow = document.querySelector('#signPassShow');
let line = document.querySelector("#LogPassShow-activeShow");
let lineSign = document.querySelector("#SignPassShow-activeShow");

let logUsername = document.querySelector('#logUsername');
let logPassword = document.querySelector('#logPassword');
let logrememberMe = document.querySelector('#remember');
let signUsername = document.querySelector('#sigUsername');
let signPassword = document.querySelector('#sigPassword');
let sigEmail = document.querySelector('#sigEmail');

let searchBarMain = document.querySelector('#mainSearchField');

let clearSearch = document.querySelector('#clearSearch');
let BrowserInput = document.querySelector('#BrowserInput');
let closeBrowser = document.querySelector('.closeBrowserBtn');


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
        if (logPassField.type === "text") {
            logPassField.type = "password";
            line.style.visibility = "hidden";
        }
        document.querySelector(".popup").classList.toggle("active");
        logUsername.value = "";
        logPassword.value = "";
        logrememberMe.checked = false;
    }
});

document.querySelector("#show-signup").addEventListener("click", function() {
    if (!document.querySelector(".popup").classList.contains("active")) {
        if (signPassField.type === "text") {
            signPassField.type = "password";
            line.style.visibility = "hidden";
        }
        document.querySelector(".popupSign").classList.toggle("active");
        signUsername.value = "";
        signPassword.value = "";
        sigEmail.value = "";
    }
});

document.querySelector(".popup .close-btn")
    .addEventListener("click", function() {
        document.querySelector(".popup").classList.remove("active");
        logPassField.type = "password";
        line.style.visibility = "hidden";
    });

document.querySelector(".popupSign .close-btn")
    .addEventListener("click", function() {
        document.querySelector(".popupSign").classList.remove("active");
        signPassField.type = "password";
        line.style.visibility = "hidden";
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

logPassShow.addEventListener("click", function() {
    if (logPassField.type === "password") {
        logPassField.type = "text";
        line.style.visibility = "visible";
    } else {
        logPassField.type = "password";
        line.style.visibility = "hidden";
    }
});

signPassShow.addEventListener("click", function() {
    if (signPassField.type === "password") {
        signPassField.type = "text";
        lineSign.style.visibility = "visible";
    } else {
        signPassField.type = "password";
        lineSign.style.visibility = "hidden";
    }
});

clearSearch.addEventListener("click", function() {
    BrowserInput.value = "";
});

searchBarMain.addEventListener("click", function() {
    let popup = document.querySelector("#popupBrowserWindow");
    console.log(popup)
    popup.classList.toggle("active");
    let blurtoggle = document.querySelector(".blur-toggle");
    blurtoggle.classList.toggle("active");
    let sidebar = document.querySelector("#sidebar");
    sidebar.classList.toggle("blurred");
});

closeBrowser.addEventListener("click", function() {
    let popup = document.querySelector("#popupBrowserWindow");
    popup.classList.toggle("active");
    let blurtoggle = document.querySelector(".blur-toggle");
    blurtoggle.classList.toggle("active");
    sidebar.classList.toggle("blurred");
});

BrowserInput.addEventListener("keyup", function(event) {
    if (event.keyCode === 13) {
        event.preventDefault();
        document.querySelector("#searchButton").click();
    }
});







