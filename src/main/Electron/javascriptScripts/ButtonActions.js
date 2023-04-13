var login = document.querySelector('#Login-button');
var logError = document.querySelector('#logError');
var SHA256 = require("crypto-js/sha256");

function getLoginPetition(username, password) {
    let xhr = new XMLHttpRequest();
    xhr.open('GET', 'http://localhost:8080/api/users/'+username+'-'+password, true);
    xhr.send();
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            console.log("login petition" + this.responseText);
            document.body.classList.add("fadeout");
            window.setTimeout(function(){
                window.location.href = "loggedIndex.html";
            },250)

        }else if(this.readyState === 4 && this.status === 404){
            console.log("Error");
            logError.innerHTML = "ERROR: Nombre o contraseña incorrectos";
            logError.style.visibility = "visible";
        }
    }
}

login.onclick = function() {
    var username = document.querySelector('#logUsername').value;
    var password = document.querySelector('#logPassword').value;
    var hashedPassword = SHA256(password).toString();
    getLoginPetition(username, hashedPassword);
}