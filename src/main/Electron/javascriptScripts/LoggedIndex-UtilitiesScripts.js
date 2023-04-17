const {ipcRenderer} = require("electron");

document.querySelector("#show-logout").addEventListener("click", function() {
    document.querySelector(".outPopup").classList.toggle("active");
});

document.querySelector(".outPopup .close-btn")
    .addEventListener("click", function() {
        document.querySelector(".outPopup").classList.remove("active");
    });

document.querySelector("#logout-cancel")
    .addEventListener("click", function() {
        document.querySelector(".outPopup").classList.remove("active");
    });

document.querySelector("#logout-accept")
    .addEventListener("click", function() {
        document.querySelector(".outPopup").classList.remove("active");
        ipcRenderer.send("logout-from-account");
        ipcRenderer.on("logout-from-account-reply", (event, arg) => {
            if(arg === true) {
                ipcRenderer.send('change-html', "index.html");
            }
        });
    });

document.querySelector("#profileImage").addEventListener("click", function() {
    document.querySelector("#file-input").click();
});

function previewFile(){
    var file = document.querySelector("#file-input").files[0];
    var img = document.querySelector("#profileImage");

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

function searchUpdate() {
    let xhr;
    let timeoutId;

    let username = $('#BrowserInput').val();

    if (username !== "") {

        if (xhr) {
            xhr.abort();
        }
        if (timeoutId) {
            clearTimeout(timeoutId);
        }

        timeoutId = setTimeout(function () {
            if (username === $('#BrowserInput').val()) {
                xhr = new XMLHttpRequest();
                xhr.open('GET', `http://localhost:8080/api/browse/${username}`, true);
                xhr.onload = function () {
                    if (xhr.readyState === 4 && xhr.status === 200) {
                        if (username === $('#BrowserInput').val()) {
                            $('#browserListContainer').html(xhr.responseText);
                        } else {
                            console.log("Error");
                        }
                    }
                };
                xhr.send();
            }
        }, 1000);
    }
}

