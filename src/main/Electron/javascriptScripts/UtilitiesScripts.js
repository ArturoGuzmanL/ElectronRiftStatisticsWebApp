const fs = require("fs");
const $ = require( "jquery" );
const {ipcRenderer} = require("electron");
const repl = require("repl");
const {getFirstChild} = require("jsdom/lib/jsdom/living/domparsing/parse5-adapter-serialization");
const Swal = require("sweetalert2");
const emailValidator = require("email-validator");
let openedOnButton = false;

$(document).ready(function() {
    $('body').addClass('loaded').css('overflow', 'auto');
    $('#entry-title').addClass("disabled");
});

$('#sidebar-button').off('click').on('click', function(event) {
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

$('#interactiveSidebar').off('mouseenter').on('mouseenter', function(event) {
    let sidebar = $('.sidebar');
    let PatchSpan = $('#PatchSpan');
    sidebar.addClass('active');
    setTimeout(() => {PatchSpan.text("Patch notes"); }, 190);
    $('.header_Browser').css('margin-left', '275px');
});

$('#interactiveSidebar').off('mouseleave').on('mouseleave', function(event) {
    let sidebar = $('.sidebar');
    let PatchSpan = $('#PatchSpan');

    if (!openedOnButton) {
        sidebar.removeClass('active');
        PatchSpan.text("Patch");
        $('.header_Browser').css('margin-left', '95px');
    }
});


$('#show-login').off('click').on('click', function(event) {
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



$('#show-signup').off('click').on('click', function(event) {
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

$('#close-btn-log').off('click').on('click', function(event) {
    let logPassField = $('#logPassword');
    let line = $('#LogPassShow-activeShow');
    $('.popup').removeClass('active');
    logPassField.attr('type', 'password');
    line.css('visibility', 'hidden');
});

$('#close-btn-sign').off('click').on('click', function(event) {
    let lineSign = $("#SignPassShow-activeShow");
    let signPassField = $("#sigPassword");
    $(".popupSign").removeClass("active");
    signPassField.attr("type", "password");
    lineSign.css("visibility", "hidden");
});

$('#logPassShow').off('click').on('click', function(event) {
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

$('#signPassShow').off('click').on('click', function(event) {
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

$('#clearSearch').off('click').on('click', function(event) {
    $('#BrowserInput').val("");
    $('#browserListContainer').html("<div class=\"loader disabled\" id=\"loader\"></div>");
});

$('#mainSearchField').off('click').on('click', function(event) {
    $('#popupBrowserWindow').toggleClass("active");
    $('#blurrDiv').toggleClass("active");
    $('#blurrDiv2').toggleClass("active");
    $('#sidebar').toggleClass("filtered");
    $(".header_object").addClass("disabled");
    $(".sidebar").addClass("disabled");
    $('#loader').addClass("disabled");
    let container = $('#browserListContainer');
    if ($("body").css("overflow") === "hidden") {
        $("body").css("overflow", "visible");
    }else {
        $("body").css("overflow", "hidden");
    }
    if (container.html() === "") {
        container.html("<div class=\"loader disabled\" id=\"loader\"></div>");
    }
    container.removeClass("loader");
});

$("#header_Browser_ta").off('click').on("click", function(event) {
    $('#popupBrowserWindow').toggleClass("active");
    $('#blurrDiv').toggleClass("active");
    $('#blurrDiv2').toggleClass("active");
    $('#sidebar').toggleClass("filtered");
    $(".header_object").addClass("disabled");
    $(".sidebar").addClass("disabled");
    $('#loader').addClass("disabled");
    let container = $('#browserListContainer');
    if ($("body").css("overflow") === "hidden") {
        $("body").css("overflow", "visible");
    }else {
        $("body").css("overflow", "hidden");
        window.scrollTo(0, 0);
    }
    if (container.html() === "") {
        container.html("<div class=\"loader disabled\" id=\"loader\"></div>");
    }
    container.removeClass("loader");

});

$('#closeBrowserBtn').off('click').on('click', function(event) {
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

$('#homePageButton').off('click').on('click', function(event) {
    ipcRenderer.send("is-logged");
    ipcRenderer.on("is-logged-reply", (event, reply) => {
        if (reply) {
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                htmlPagesRequests("http://localhost:8080/api/htmlRequests/home/" + reply + "/" + uid, "logged")
            });
        }else {
            htmlPagesRequests("http://localhost:8080/api/htmlRequests/home/" + reply + "/null", "unlogged")
        }
    });
});

$('#championsPageButton').off('click').on('click', function(event) {
    ipcRenderer.send("is-logged");
    ipcRenderer.on("is-logged-reply", (event, reply) => {
        if (reply) {
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                htmlPagesRequests("http://localhost:8080/api/htmlRequests/championlist/"+reply+"/"+uid, "logged")
            });
        }else {
            htmlPagesRequests("http://localhost:8080/api/htmlRequests/championlist/"+reply+"/null", "unlogged")
        }
    });
});

$('#itemsPageButton').off('click').on('click', function(event) {
    ipcRenderer.send("is-logged");
    ipcRenderer.on("is-logged-reply", (event, reply) => {
        if (reply) {
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                htmlPagesRequests("http://localhost:8080/api/htmlRequests/itemlist/"+reply+"/"+uid, "logged")
            });
        }else {
            htmlPagesRequests("http://localhost:8080/api/htmlRequests/itemlist/"+reply+"/null", "unlogged")
        }
    });
});

$('#browserListContainer').off('click', 'li.browserItem').on('click', 'li.browserItem', function() {
    getSummoner.call(this, true);
});

$('#recentlyPlayedContent').off('click', 'div.recentlyPlayedSummoner').on('click', 'div.recentlyPlayedSummoner', function() {
    getSummoner.call(this, false);
});

function getSummoner(isBrowser) {
    let summID = $(this).attr("id");
    let region;
    if (isBrowser) {
        region = $('#summoner'+summID+'Region').text();
    }else {
        region = $('#summoner'+summID+'Region').children(0).attr("id");
    }
    ipcRenderer.send("is-logged");
    ipcRenderer.on("is-logged-reply", (event, reply) => {
        if (reply) {
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                htmlPagesRequests("http://localhost:8080/api/htmlRequests/summonerPage/" + summID + "/" + region + "/" + reply + "/" + uid, "logged")
            });
        } else {
            htmlPagesRequests("http://localhost:8080/api/htmlRequests/summonerPage/" + summID + "/" + region + "/" + reply + "/null", "unlogged")
        }
    });
}

function htmlPagesRequests(url, pageType) {
    $('body').removeClass('loaded').css('overflow', 'hidden');
    $('#page').html("");
    $('#entry-title').removeClass("disabled");
    let fileName = "ElectronPage.html";
    setTimeout(function() {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', `${url}`, false);
        xhr.onload = function () {
            if (xhr.readyState === 4 && xhr.status === 200) {
                ipcRenderer.send("get-tempfiles-folder");
                ipcRenderer.on("get-tempfiles-folder-reply", (event, reply) => {
                    fs.writeFileSync(reply + "/ElectronPage.html", xhr.responseText);
                });
                ipcRenderer.send("change-html");
            }else {
                console.log("Error page");
            }
        }
        xhr.send();
    }, 15);
}

$("#soloqButton").off('click').on("click", function(event) {
    $(".eloInfoFlex").removeClass("active");
    $(".eloInfoSolo").removeClass("disabled");
    $(".flexqButton").removeClass("active");
    $(".soloqButton").removeClass("disabled");
});

$("#flexqButton").off('click').on("click", function(event) {
    $(".eloInfoFlex").addClass("active");
    $(".eloInfoSolo").addClass("disabled");
    $(".flexqButton").addClass("active");
    $(".soloqButton").addClass("disabled");
});

$(document).ready(function() {
    const itemListContainer = $('#itemListContainer');
    const itemImages = $('.ItemObject');
    const itemTooltips = $('.itemTooltip');

    itemImages.off('mouseleave');
    itemImages.mouseleave(function() {
        const tooltip = $(this).next();
        tooltip.css('display', 'none');
    });
    itemImages.off('mouseenter');
    itemImages.mouseenter(function() {
        const tooltip = $(this).next();
        tooltip.css('display', 'flex');
    });

    itemImages.off('hover');
    itemImages.hover(function(e) {
        const itemImage = $(this);
        const itemImageRect = itemImage[0].getBoundingClientRect();
        const tooltip = $(itemImage.next());
        const tooltipRect = tooltip[0].getBoundingClientRect();
        const containerRect = itemListContainer[0].getBoundingClientRect();
        const itemImageXl = itemImage.offset().left - containerRect.left + itemImageRect.width;
        const itemImageYt = itemImage.offset().top - containerRect.top - itemImageRect.height + 10 - window.scrollY;
        const tooltipWidth = tooltip.outerWidth();
        const tooltipHeight = tooltip.outerHeight();

        let tooltipLeft = itemImageXl;
        let tooltipTop = itemImageYt;

        const spaceBelow = window.innerHeight - (itemImageYt - scrollY + 40);
        if (spaceBelow < tooltipRect.height+297) {
            tooltipTop = itemImageYt - tooltipRect.height - 15;
        }

        if (tooltipLeft + tooltipWidth > window.innerWidth - 240) {
            tooltipLeft = itemImage.offset().left - containerRect.left - tooltipWidth - 10;
        }

        tooltip.css({
            left: tooltipLeft + 'px',
            top: tooltipTop + 'px'
        });
    });
});

// ******************************************************
// Button actions scripts
// ******************************************************

$('#Login-button').off('click').on('click', function(event) {
    const username = $('#logUsername').val();
    const password = $('#logPassword').val();
    const remember = $('#rememberMe').is(":checked");
    const { ipcRenderer } = require('electron');
    ipcRenderer.send("encrypt-text", password);
    ipcRenderer.on("encrypt-text-reply", (event, hashedPassword) => {
        getLoginPetition(username, hashedPassword, remember);
    });
});

function getLoginPetition(username, password, remember) {
    const { ipcRenderer } = require('electron');
    const fs = require("fs");
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:8080/api/users/actions/login/${username}=${password}`, false);
    xhr.send();
    let id = "";
    let logCorrect = false;
    if (xhr.readyState === 4 && xhr.status === 200) {
        logCorrect = true;
        id = xhr.responseText;
        const rememberValue = remember ? "True" : "False";
        const data = `ID=${id};Remember=${rememberValue}`;
        ipcRenderer.send("get-tempcache-path");
        ipcRenderer.on("get-tempcache-path-reply", (event, path) => {
            if (!fs.existsSync(path)) {
                fs.mkdirSync(path);
            }
            const dirPath = path + "\\Session";
            if (!fs.existsSync(dirPath)) {
                fs.mkdirSync(dirPath);
            }
            const filePath = dirPath + "\\Session.txt";
            fs.writeFileSync(filePath, data, { encoding: 'utf8' });
        });
    } else if (xhr.readyState === 4 && xhr.status === 404) {
        console.log("Error");
        const Toast = Swal.default.mixin({
            toast: true,
            position: 'top',
            color: '#FFFFFF',
            background: 'rgba(11, 11, 35, 1)',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.default.stopTimer)
                toast.addEventListener('mouseleave', Swal.default.resumeTimer)
            }
        })

        Toast.fire({
            icon: 'error',
            title: "Incorrect username or password"
        })
        return;
    }
    Swal.default.fire({
        title: "Login Successful",
        color: '#FFFFFF',
        showDenyButton: false,
        showCancelButton: false,
        confirmButtonText: `Ok`,
        background: 'rgba(11, 11, 35, 1)',
    }).then((result) => {
        if (logCorrect) {
            ipcRenderer.send("get-tempfiles-folder");
            ipcRenderer.on("get-tempfiles-folder-reply", (event, path) => {
                const xhr2 = new XMLHttpRequest();
                xhr2.open('GET', `http://localhost:8080/api/htmlRequests/home/initialization/true/${id}`, true);
                xhr2.onload = function() {
                    if (xhr2.readyState === 4 && xhr2.status === 200) {
                        const filename = "\\ElectronPage.html";
                        const filePath = path + filename;
                        if (!fs.existsSync(path)) {
                            fs.mkdirSync(path);
                        }
                        fs.writeFileSync(filePath, xhr2.responseText, { encoding: 'utf8' });
                        console.log(filePath);
                        ipcRenderer.send('change-html', filePath);
                    } else {
                        console.log("Error");
                        $('#logError').css('visibility', 'visible').html("ERROR: "+xhr2.status);
                    }
                }
                xhr2.send();
            });
        }
    });
}

$('#Signup-button').off('click').on('click', function(event) {
    const username = $('#sigUsername').val();
    const password = $('#sigPassword').val();
    const email = $('#sigEmail').val();
    let reply = SignUpActionValidator(username, password, email);
    let xhr;
    if (reply !== "") {
        const Toast = Swal.default.mixin({
            toast: true,
            position: 'top',
            color: '#FFFFFF',
            background: 'rgba(11, 11, 35, 1)',
            showConfirmButton: false,
            timer: 3000,
            timerProgressBar: true,
            didOpen: (toast) => {
                toast.addEventListener('mouseenter', Swal.default.stopTimer)
                toast.addEventListener('mouseleave', Swal.default.resumeTimer)
            }
        })

        Toast.fire({
            icon: 'error',
            title: reply
        })
    } else {
        xhr = new XMLHttpRequest();
        let data = "";
        if (password%2 === 0) {
            let temppass1 = password.substring(0, password.length/2);
            let temppass2 = password.substring(password.length/2, password.length);
            data = temppass1 + "=" + username + "=" + email + "=" + temppass2;
        } else {
            let middleIndex = Math.floor(password.length / 2);
            let temppass1 = password.substring(0, middleIndex);
            let temppass2 = password.charAt(middleIndex);
            let temppass3 = password.substring(middleIndex + 1, password.length);
            data = temppass1 + "=" + username + "=" + temppass2 + email + "=" + temppass3;
        }
        xhr.open('POST', `http://localhost:8080/api/users/actions/signup/${data}`, false);
        xhr.onload = function() {
            if (xhr.readyState === 4 && xhr.status === 201) {
                console.log("User created");
                const Toast = Swal.default.mixin({
                    toast: true,
                    color: '#FFFFFF',
                    background: 'rgba(11, 11, 35, 1)',
                    position: 'top',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.default.stopTimer)
                        toast.addEventListener('mouseleave', Swal.default.resumeTimer)
                    }
                })

                let lineSign = $("#SignPassShow-activeShow");
                let signPassField = $("#sigPassword");
                $(".popupSign").removeClass("active");
                signPassField.attr("type", "password");
                lineSign.css("visibility", "hidden");
                Toast.fire({
                    icon: 'success',
                    title: 'Account created successfully'
                })
            } else {
                let lineSign = $("#SignPassShow-activeShow");
                let signPassField = $("#sigPassword");
                signPassField.attr("type", "password");
                lineSign.css("visibility", "hidden");
                signPassField.val("");
                const Toast = Swal.default.mixin({
                    toast: true,
                    position: 'top',
                    color: '#FFFFFF',
                    background: 'rgba(11, 11, 35, 1)',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                    didOpen: (toast) => {
                        toast.addEventListener('mouseenter', Swal.default.stopTimer)
                        toast.addEventListener('mouseleave', Swal.default.resumeTimer)
                    }
                })

                Toast.fire({
                    icon: 'error',
                    title: 'Error type: ' + xhr.responseText
                })
            }
        }
        xhr.send();
    }
});


function SignUpActionValidator(username, password, email) {
    const emailValidator = require('email-validator');

    if (isWithinLengthLimits(username, 4, 20)) {
        return "Username must be between 4 and 20 characters";
    }
    if (isWithinLengthLimits(password, 8, 20)) {
        return "Password must be between 8 and 20 characters";
    }
    if (isWithinLengthLimits(email, 1, 50)) {
        return "Email cannot have more than 50 characters";
    }
    if (!emailValidator.validate(email)) {
        return "Invalid email address";
    }
    if (containsInvalidCharacters(username)) {
        return "Username can oly contain letters and numbers";
    }

    return "";
}

function isWithinLengthLimits(string, minLength, maxLength) {
    return string.length < minLength || string.length > maxLength;
}

function containsInvalidCharacters(string) {
    return string.match(/[^a-zA-Z0-9]+/);
}

$('#BrowserInput').off('click').on('input', function(event) {
    let xhr;
    let timeoutId;
    let loading;

    let username = $('#BrowserInput').val();

    if (username !== "") {
        let container = $('#browserListContainer');
        if (container.html() === "") {
            container.html("<div class=\"loader disabled\" id=\"loader\"></div>");
        }

        if (xhr) {
            xhr.abort();
        }
        if (timeoutId) {
            clearTimeout(timeoutId);
        }

        $('#loader').removeClass("disabled");
        $('#browserListContainer').addClass("loader");

        timeoutId = setTimeout(function () {
            if (username === $('#BrowserInput').val()) {
                ipcRenderer.send("get-appid");
                ipcRenderer.on("get-appid-reply", (event, appid) => {
                    xhr = new XMLHttpRequest();
                    xhr.open('GET', `http://localhost:8080/api/browse/${username}/${appid}`, true);
                    xhr.onload = function () {
                        if (xhr.readyState === 4 && xhr.status === 200) {
                            if (username === $('#BrowserInput').val()) {
                                $('#loader').addClass("disabled");
                                $('#browserListContainer').removeClass("loader").html(xhr.responseText);
                                loading = false;
                            } else {
                                console.log("Error");
                                $('#loader').addClass("disabled");
                                $('#browserListContainer').removeClass("loader").html("<div class=\"loader disabled\" id=\"loader\"></div>");
                                loading = false;
                            }
                        }
                    };
                    xhr.send();
                });
            }
        }, 1150);
    }else {
        $('#browserListContainer').html("");
    }
});

$('#BrowserInput').off('click').on('keydown', function(event) {
    let browserListContainer = $('#browserListContainer');
    let browserInput = $('#BrowserInput').val();

    if (event.which === 8) { // El usuario ha pulsado la tecla borrar
        if (browserListContainer.html() !== "<div class=\"loader disabled\" id=\"loader\"></div>") {
            browserListContainer.html(""); // Borramos el contenido
        }

        if (browserInput !== "") {
            if (!browserListContainer.hasClass("loader")) {
                browserListContainer.addClass("loader").html("<div class=\"loader disabled\" id=\"loader\"></div>");
                $('#loader').removeClass("disabled");
            }
        }else {
            $('#browserListContainer').html("");
        }
    }
});

// ******************************************************
// Logged utilities scripts
// ******************************************************

$('#show-logout').on('click', function(event) {
    $("#outPopup").toggleClass("active");
});

$('#close-btn').on('click', function(event) {
    $("#outPopup").removeClass("active");
});

$('#logout-cancel').on('click', function(event) {
    $("#outPopup").removeClass("active");
});

$('#logout-accept').on('click', function(event) {
    $("#outPopup").removeClass("active");
    ipcRenderer.send("logout-from-account");
    ipcRenderer.on("logout-from-account-reply", (event, arg) => {
        if(arg === true) {
            let xhr = new XMLHttpRequest();
            xhr.open("GET", "http://localhost:8080/api/htmlRequests/home/initialization/false/null", true);
            xhr.onload = function () {
                if (xhr.readyState === 4 && xhr.status === 200) {
                    ipcRenderer.send("get-tempfiles-folder");
                    ipcRenderer.on("get-tempfiles-folder-reply", (event, tempFilesFolder) => {
                        const filename = "\\ElectronPage.html";
                        const filePath = tempFilesFolder + filename;
                        if (!fs.existsSync(tempFilesFolder)) {
                            fs.mkdirSync(tempFilesFolder);
                        }
                        fs.writeFileSync(filePath, xhr.responseText, { encoding: 'utf8' });
                        console.log(filePath);
                        ipcRenderer.send('change-html', filePath);
                    });
                } else {
                    console.log("Error");
                    $('#logError').css('visibility', 'visible').html("ERROR: "+xhr.status);
                }
            }
            xhr.send();
        }
    });
});

$('#profileImage').on('click', function(event) {
    $("#file-input").click();
});

function previewFile(){
    var file = $('#file-input').prop('files')[0];
    var img = $('#profileImage').get(0);

    if(file){
        var reader = new FileReader();
        reader.onload = function(){
            img.src = reader.result;
            console.log(reader.result);
        }

        reader.readAsDataURL(file);

        const reader2 = new FileReader();
        reader2.addEventListener("load", function () {
            const base64Image = btoa(reader2.result);
            const imgNormal = Buffer.from(base64Image, "base64");

            const xhr = new XMLHttpRequest();
            ipcRenderer.send("get-uid");
            ipcRenderer.on("get-uid-reply", (event, uid) => {
                xhr.open("POST", `http://localhost:8080/api/users/actions/profileimgupdt/${uid}`);
                xhr.send(imgNormal);
            });
        });

        reader2.readAsBinaryString(file)
    }
}
