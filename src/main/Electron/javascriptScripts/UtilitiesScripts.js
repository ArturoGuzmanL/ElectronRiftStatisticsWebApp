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
        $('.header_Browser').css('margin-left', '275px');
    } else {
        PatchSpan.text("Patch");
        openedOnButton = false;
        $('.header_Browser').css('margin-left', '95px');
    }

});

$('#interactiveSidebar').on('mouseenter', function(event) {
    let sidebar = $('.sidebar');
    let PatchSpan = $('#PatchSpan');
    sidebar.addClass('active');
    setTimeout(() => {PatchSpan.text("Patch notes"); }, 190);
    $('.header_Browser').css('margin-left', '275px');
});

$('#interactiveSidebar').on('mouseleave', function(event) {
    let sidebar = $('.sidebar');
    let PatchSpan = $('#PatchSpan');

    if (!openedOnButton) {
        sidebar.removeClass('active');
        PatchSpan.text("Patch");
        $('.header_Browser').css('margin-left', '95px');
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

$("#header_Browser_ta").on("click", function(event) {
    $('#popupBrowserWindow').toggleClass("active");
    $('#blurrDiv').toggleClass("active");
    $('#blurrDiv2').toggleClass("active");
    $('#sidebar').toggleClass("filtered");
    $(".header_object").addClass("disabled");
    $(".sidebar").addClass("disabled");
    $('#loader').addClass("disabled");
    $('#browserListContainer').removeClass("loader");
    if ($("body").css("overflow") === "hidden") {
        $("body").css("overflow", "visible");
    }else {
        $("body").css("overflow", "hidden");
        window.scrollTo(0, 0);
    }

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
    if ($("body").css("overflow") === "hidden") {
        $("body").css("overflow", "visible");
    }
});

$('#homePageButton').on('click', function(event) {
    console.log("Se ha ejecutado homePage");
    ipcRenderer.send("is-logged");
    ipcRenderer.on("is-logged-reply", (event, reply) => {
        if (reply) {
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                htmlPagesRequests("http://localhost:8080/api/htmlRequests/home/" + reply + "/" + uid, "ElectronPage.html")
            });
        }else {
            htmlPagesRequests("http://localhost:8080/api/htmlRequests/home/" + reply + "/null", "ElectronPage.html")
        }
    });
});

$('#championsPageButton').on('click', function(event) {
    console.log("Se ha ejecutado championsPage");
    ipcRenderer.send("is-logged");
    ipcRenderer.on("is-logged-reply", (event, reply) => {
        if (reply) {
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                htmlPagesRequests("http://localhost:8080/api/htmlRequests/championlist/"+reply+"/"+uid, "ElectronPage.html")
            });
        }else {
            htmlPagesRequests("http://localhost:8080/api/htmlRequests/championlist/"+reply+"/null", "ElectronPage.html")
        }
    });
});

function htmlPagesRequests(url, fileName) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', `${url}`, false);
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            ipcRenderer.send("get-tempfiles-folder");
            ipcRenderer.on("get-tempfiles-folder-reply", (event, tempfilesFolder) => {
                if (!fs.existsSync(tempfilesFolder)) {
                    fs.mkdirSync(tempfilesFolder, { recursive: true })
                }
                console.log(`${tempfilesFolder}/${fileName}`)
                fs.writeFile(`${tempfilesFolder}/${fileName}`, xhr.responseText, (err) => {
                    if (err) {
                        console.log(err);
                    }
                });
                console.log(`${tempfilesFolder}/${fileName}`)
                ipcRenderer.send("change-html", `${tempfilesFolder}/${fileName}`);
            });
        }else {
            console.log("Error page");
        }
    }
    xhr.send();
}

$("#soloqButton").on("click", function(event) {
    $(".eloInfoFlex").removeClass("active");
    $(".eloInfoSolo").removeClass("disabled");
    $(".flexqButton").removeClass("active");
    $(".soloqButton").removeClass("disabled");
});

$("#flexqButton").on("click", function(event) {
    $(".eloInfoFlex").addClass("active");
    $(".eloInfoSolo").addClass("disabled");
    $(".flexqButton").addClass("active");
    $(".soloqButton").addClass("disabled");
});

$('#browserListContainer').on('click', 'li.browserItem', function() {
    getSummoner.call(this);
});

$('#recentlyPlayedContent').on('click', 'div.recentlyPlayedSummoner', function() {
    getSummoner.call(this);
});

function getSummoner() {
    let summID = $(this).attr("id");
    ipcRenderer.send("is-logged");
    ipcRenderer.on("is-logged-reply", (event, reply) => {
        if (reply) {
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                htmlPagesRequests("http://localhost:8080/api/htmlRequests/summonerPage/" + summID + "/" + reply + "/" + uid, "ElectronPage.html")
            });
        } else {
            htmlPagesRequests("http://localhost:8080/api/htmlRequests/summonerPage/" + summID + "/" + reply + "/null", "ElectronPage.html")
        }
    });
}
