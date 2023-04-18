const fs = require("fs");
const $ = require( "jquery" );
const {ipcRenderer} = require("electron");
let openedOnButton = false;


$('#sidebar-button').on('click', function(event) {
    let sidebar = $('.sidebar');
    let PatchSpan = $('#PatchSpan');
    sidebar.toggleClass('active');
    if (sidebar.hasClass('active')) {
        setTimeout(() => {PatchSpan.text("Patch notes"); }, 190);
        openedOnButton = true;
        $('.nav_buttons').css('margin-left', '215px');
    } else {
        PatchSpan.text("Patch");
        openedOnButton = false;
        $('.nav_buttons').css('margin-left', '45px');
    }

});

$('#interactiveSidebar').on('mouseenter', function(event) {
    let sidebar = $('.sidebar');
    let PatchSpan = $('#PatchSpan');
    sidebar.addClass('active');
    setTimeout(() => {PatchSpan.text("Patch notes"); }, 190);
    $('.nav_buttons').css('margin-left', '215px');
});

$('#interactiveSidebar').on('mouseleave', function(event) {
    let sidebar = $('.sidebar');
    let PatchSpan = $('#PatchSpan');

    if (!openedOnButton) {
        sidebar.removeClass('active');
        PatchSpan.text("Patch");
        $('.nav_buttons').css('margin-left', '45px');
    }
});


$('#show-login').on('click', function(event) {
    let line = $("#LogPassShow-activeShow");
    let logPassField = $("#logPassword");
    let logUsername = $("#logUsername");
    let logrememberMe = $("#remember");

    if (!$(".popupSign").hasClass("active")) {
        if (logPassField.attr("type") === "text") {
            logPassField.attr("type", "password");
            line.css("visibility", "hidden");
        }
        $(".popup").toggleClass("active");
        logUsername.val("");
        logPassField.val("");
        logrememberMe.prop("checked", false);
    }

});



$('#show-signup').on('click', function(event) {
    let lineSign = $("#SignPassShow-activeShow");
    let signPassField = $("#sigPassword");
    if (!$(".popup").hasClass("active")) {
        if (signPassField.attr("type") === "text") {
            signPassField.attr("type", "password");
            lineSign.css("visibility", "hidden");
        }
        $(".popupSign").toggleClass("active");
        $("#sigUsername").val("");
        $("#sigPassword").val("");
        $("#sigEmail").val("");
    }
});

$('#close-btn-log').on('click', function(event) {
    let logPassField = $('#logPassword');
    let line = $('#LogPassShow-activeShow');
    $('.popup').removeClass('active');
    logPassField.attr('type', 'password');
    line.css('visibility', 'hidden');
});

$('#close-btn-sign').on('click', function(event) {
    let lineSign = $("#SignPassShow-activeShow");
    let signPassField = $("#sigPassword");
    $(".popupSign").removeClass("active");
    signPassField.attr("type", "password");
    lineSign.css("visibility", "hidden");
});

$('#back-button').on('click', function(event) {
    history.back();
});

$('#reload-button').on('click', function(event) {
    const { ipcRenderer } = require('electron');
    const html = window.location.pathname.split('/').pop();
    ipcRenderer.send("get-tempcache-path");
    ipcRenderer.on("get-tempcache-path-reply", (event, path) => {
        if (fs.existsSync(path)) {
            const tempFilsPath = __dirname + "\\TempHtmlFiles\\";
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
        }else {
            location.reload();
        }
    });
});

$('#forward-button').on('click', function(event) {
    history.forward();
});

$('#logPassShow').on('click', function(event) {
    let logPassField = $('#logPassword');
    let line = $('#LogPassShow-activeShow');
    if (logPassField.attr('type') === 'password') {
        logPassField.attr('type', 'text');
        line.css('visibility', 'visible');
    } else {
        logPassField.attr('type', 'password');
        line.css('visibility', 'hidden');
    }
});

$('#signPassShow').on('click', function(event) {
    let signPassField = $("#sigPassword");
    let lineSign = $("#SignPassShow-activeShow");
    if (signPassField.attr("type") === "password") {
        signPassField.attr("type", "text");
        lineSign.css("visibility", "visible");
    } else {
        signPassField.attr("type", "password");
        lineSign.css("visibility", "hidden");
    }
});

$('#clearSearch').on('click', function(event) {
    $('#BrowserInput').val("");
    $('#browserListContainer').html("<div class=\"loader disabled\" id=\"loader\"></div>");
});

$('#mainSearchField').on('click', function(event) {
    $('#popupBrowserWindow').toggleClass("active");
    $('#blurrDiv').toggleClass("active");
    $('#blurrDiv2').toggleClass("active");
    $('#sidebar').toggleClass("filtered");
    $(".header_object").addClass("disabled");
    $(".sidebar").addClass("disabled");
    $('#loader').addClass("disabled");
    $('#browserListContainer').removeClass("loader");
});

$('#closeBrowserBtn').on('click', function(event) {
    $('#popupBrowserWindow').toggleClass("active");
    $('#blurrDiv').toggleClass("active");
    $('#blurrDiv2').toggleClass("active");
    $('#sidebar').toggleClass("filtered");
    $("#BrowserInput").val("");
    $('#browserListContainer').html("").removeClass("loader");
    $(".header_object").removeClass("disabled");
    $(".sidebar").removeClass("disabled");
    $('#loader').addClass("disabled");
});
