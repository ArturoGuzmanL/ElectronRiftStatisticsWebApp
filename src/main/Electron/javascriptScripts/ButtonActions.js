const path = require("path");
require("repl");
const $ = require( "jquery" );
const {ipcRenderer} = require("electron");
const Swal = require('sweetalert2');
const fs = require("fs");


$('#Login-button').on('click', function(event) {
    const username = document.querySelector('#logUsername').value;
    const password = document.querySelector('#logPassword').value;
    const remember = document.querySelector('#remember').checked;
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
                xhr2.open('GET', `http://localhost:8080/api/htmlRequests/home/true/${id}`, true);
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

$('#Signup-button').on('click', function(event) {
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

$('#BrowserInput').on('input', function(event) {
    let xhr;
    let timeoutId;
    let loading;

    let username = $('#BrowserInput').val();

    if (username !== "") {

        if (xhr) {
            xhr.abort();
        }
        if (timeoutId) {
            clearTimeout(timeoutId);
        }

        $('#loader').removeClass("disabled");
        $('#browserListContainer').addClass("loader");

        ipcRenderer.send("get-appid");
        ipcRenderer.on("get-appid-reply", (event, appid) => {
            timeoutId = setTimeout(function () {
                if (username === $('#BrowserInput').val()) {
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
                }
            }, 1000);
        });
    }else {
        $('#browserListContainer').html("");
    }
});

$('#BrowserInput').on('keydown', function(event) {
    let browserListContainer = $('#browserListContainer');
    let browserInput = $('#BrowserInput').val();

    if (event.which === 8) { // El usuario ha pulsado la tecla borrar
        browserListContainer.html(""); // Borramos el contenido

        if (browserInput !== "") {
            browserListContainer.addClass("loader").html("<div class=\"loader disabled\" id=\"loader\"></div>");
            $('#loader').removeClass("disabled");
        }else {
            $('#browserListContainer').html("");
        }
    }
});
