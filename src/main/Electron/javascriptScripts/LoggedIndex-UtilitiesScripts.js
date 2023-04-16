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
        console.log("logout realized");
        document.body.classList.add("fadeout");
        window.setTimeout(function(){
            window.location.href = "Index.html";
        },250)
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
                xhr.open("POST", `http://localhost:8080/api/users/profileimgupdt/${uid}`);
                xhr.send(imgNormal);
            });
        });

        reader2.readAsBinaryString(file)
    }
}
