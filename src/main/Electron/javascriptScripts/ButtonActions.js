const logError = document.querySelector('#logError');
const path = require("path");



function getLoginPetition(username, password, remember) {
    const { ipcRenderer } = require('electron');
    const fs = require("fs");
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:8080/api/users/${username}-${password}`, false);
    xhr.send();
    let id = "";
    let logCorrect = false;
    if (xhr.readyState === 4 && xhr.status === 200) {
        id = xhr.responseText;
        logCorrect = true;
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
        logError.innerHTML = "ERROR: Nombre o contraseña incorrectos";
        logError.style.visibility = "visible";
        return;
    }
    if (logCorrect) {
        ipcRenderer.send("get-tempcache-path");
        ipcRenderer.on("get-tempcache-path-reply", (event, path) => {
            const tempFilsPath = path + "\\Files";
            if (!fs.existsSync(tempFilsPath)) {
                fs.mkdirSync(tempFilsPath);
            }

            const xhr2 = new XMLHttpRequest();
            xhr2.open('GET', `http://localhost:8080/api/htmlRequests/login/${id}`, true);
            xhr2.onload = function() {
                if (xhr2.readyState === 4 && xhr2.status === 200) {
                    const filename = "\\loggedPage.html";
                    const filePath = tempFilsPath + filename;
                    fs.writeFileSync(filePath, xhr2.responseText, { encoding: 'utf8' });
                    console.log(filePath);
                    ipcRenderer.send('change-html', filePath);
                } else {
                    console.log("Error");
                    logError.innerHTML = "ERROR: Nombre o contraseña incorrectos";
                    logError.style.visibility = "visible";
                }
            }
            xhr2.send();
        });
    }
}

function loginActionPetition(){
    const username = document.querySelector('#logUsername').value;
    const password = document.querySelector('#logPassword').value;
    const remember = document.querySelector('#remember').checked;
    const { ipcRenderer } = require('electron');
    ipcRenderer.send("encrypt-text", password);
    ipcRenderer.on("encrypt-text-reply", (event, hashedPassword) => {
        getLoginPetition(username, hashedPassword, remember);
    });
}

function SignInActionValidator(username, password, email) {
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
        return "Username cannot contain spaces, single quotes, or double quotes";
    }
    if (containsInvalidCharacters(password)) {
        return "Password cannot contain spaces, single quotes, or double quotes";
    }
    if (containsInvalidCharacters(email)) {
        return "Email cannot contain spaces, single quotes, or double quotes";
    }

    return "";
}

function isWithinLengthLimits(string, minLength, maxLength) {
    return string.length < minLength || string.length > maxLength;
}

function containsInvalidCharacters(string) {
    return string.match(/.*[\s'"`].*/);
}