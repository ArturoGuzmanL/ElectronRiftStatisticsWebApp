const logError = document.querySelector('#logError');
const sigError = document.querySelector('#sigError');
const path = require("path");
const repl = require("repl");



function getLoginPetition(username, password, remember) {
    const { ipcRenderer } = require('electron');
    const fs = require("fs");
    const xhr = new XMLHttpRequest();
    xhr.open('GET', `http://localhost:8080/api/users/actions/login/${username}=${password}`, false);
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
        logError.innerHTML = "ERROR: name or password incorrect";
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
                    logError.innerHTML = "ERROR: "+xhr2.status
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

function signupActionPetition() {
    const username = document.querySelector('#sigUsername').value;
    const password = document.querySelector('#sigPassword').value;
    const email = document.querySelector('#sigEmail').value;
    const { ipcRenderer } = require('electron');
    let reply = SignUpActionValidator(username, password, email);
    let xhr;
    if (reply !== "") {
        sigError.textContent = reply;
        sigError.style.visibility = "visible";
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
                sigError.textContent = "User created";
                sigError.style.visibility = "visible";
                sigError.style.color = "white";
            } else {
                sigError.textContent = "ERROR: "+xhr.status
                sigError.style.visibility = "visible";
            }
        }
        xhr.send();
    }
}


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