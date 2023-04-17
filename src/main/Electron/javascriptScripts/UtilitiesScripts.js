const fs = require("fs");
window.$ = window.jQuery = require('jquery');

let btn = document.querySelector('#sidebar-button');
let sidebarId = document.querySelector('#interactiveSidebar');
let sidebar = document.querySelector('.sidebar');
let PatchSpan = document.querySelector('#PatchSpan');
let backButton = document.querySelector('#back-button');
let reloadButton = document.querySelector('#reload-button');
let forwardButton = document.querySelector('#forward-button');
let openedOnButton = false;

let logUsername = document.querySelector('#logUsername');
let logPassword = document.querySelector('#logPassword');
let logrememberMe = document.querySelector('#remember');
let signUsername = document.querySelector('#sigUsername');
let signPassword = document.querySelector('#sigPassword');
let sigEmail = document.querySelector('#sigEmail');
let searchBarMain = document.querySelector('#mainSearchField');
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


function openPopUpLog() {
    let logPassField = document.querySelector('#logPassword');
    let line = document.querySelector("#LogPassShow-activeShow");
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
}


function openPopUpSign() {
    let lineSign = document.querySelector("#SignPassShow-activeShow");
    let signPassField = document.querySelector("#sigPassword");
    if (!document.querySelector(".popup").classList.contains("active")) {
        if (signPassField.type === "text") {
            signPassField.type = "password";
            lineSign.style.visibility = "hidden";
        }
        document.querySelector(".popupSign").classList.toggle("active");
        signUsername.value = "";
        signPassword.value = "";
        sigEmail.value = "";
    }
}

function closeLogIn() {
    let logPassField = document.querySelector('#logPassword');
    let line = document.querySelector("#LogPassShow-activeShow");
    document.querySelector(".popup").classList.remove("active");
    logPassField.type = "password";
    line.style.visibility = "hidden";
}

function closeSignUp() {
    let lineSign = document.querySelector("#SignPassShow-activeShow");
    let signPassField = document.querySelector("#sigPassword");
    document.querySelector(".popupSign").classList.remove("active");
    signPassField.type = "password";
    lineSign.style.visibility = "hidden";
}

backButton.addEventListener("click", function() {
    history.back();
});

reloadButton.addEventListener("click", function() {
    const { ipcRenderer } = require('electron');
    const html = window.location.pathname.split('/').pop();
    ipcRenderer.send("get-tempcache-path");
    ipcRenderer.on("get-tempcache-path-reply", (event, path) => {
        const tempFilsPath = path + "\\Files\\";
        if (!fs.existsSync(tempFilsPath)) {
            fs.mkdirSync(tempFilsPath);
        }

        const xhr = new XMLHttpRequest();
        let filename;
        if (html==="loggedPage.html") {
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                xhr.open('GET', `http://localhost:8080/api/htmlRequests/login/${uid}`, true);
                filename = "loggedPage.html";
                xhr.onload = function() {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        const filePath = tempFilsPath + filename;
                        if (fs.existsSync(filePath)) {
                            fs.unlinkSync(filePath);
                        }
                        console.log(filePath)
                        fs.writeFileSync(filePath, xhr.responseText, { encoding: 'utf8' });
                        ipcRenderer.send('change-html', filePath);
                    } else {
                        console.log("Error");
                    }
                }
                xhr.send();
            });
        }

    });
});

forwardButton.addEventListener("click", function() {
    history.forward();
});

function logShowPass() {
    let logPassField = document.querySelector('#logPassword');
    let line = document.querySelector("#LogPassShow-activeShow");
    if (logPassField.type === "password") {
        logPassField.type = "text";
        line.style.visibility = "visible";
    } else {
        logPassField.type = "password";
        line.style.visibility = "hidden";
    }
}

function signShowPass() {
    let signPassField = document.querySelector("#sigPassword");
    let lineSign = document.querySelector("#SignPassShow-activeShow");
    if (signPassField.type === "password") {
        signPassField.type = "text";
        lineSign.style.visibility = "visible";
    } else {
        signPassField.type = "password";
        lineSign.style.visibility = "hidden";
    }
}

function clearSearch() {
    let BrowserInput = document.querySelector('#BrowserInput');
    BrowserInput.value = "";
    $('#browserListContainer').html("<div class=\"loader disabled\" id=\"loader\"></div>");
}

searchBarMain.addEventListener("click", function() {
    let popup = document.querySelector("#popupBrowserWindow");
    popup.classList.toggle("active");
    let blurtoggle = document.querySelector(".blur-toggle");
    blurtoggle.classList.toggle("active");
    let sidebar = document.querySelector("#sidebar");
    sidebar.classList.toggle("blurred");
    $(".header_object").addClass("disabled");
    $(".sidebar").addClass("disabled");
    $('#loader').addClass("disabled");
    $('#browserListContainer').removeClass("loader");
});

closeBrowser.addEventListener("click", function() {
    let popup = document.querySelector("#popupBrowserWindow");
    popup.classList.toggle("active");
    let blurtoggle = document.querySelector(".blur-toggle");
    blurtoggle.classList.toggle("active");
    sidebar.classList.toggle("blurred");
    $("#BrowserInput").val("");
    $('#browserListContainer').html("");
    $(".header_object").removeClass("disabled");
    $(".sidebar").removeClass("disabled");
    $('#loader').addClass("disabled");
    $('#browserListContainer').removeClass("loader");
});
