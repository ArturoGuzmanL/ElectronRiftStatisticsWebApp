const {ipcRenderer} = require("electron");
const $ = require( "jquery" );
const fs = require("fs");

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
            xhr.open("GET", "http://localhost:8080/api/htmlRequests/home/false/null", true);
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

