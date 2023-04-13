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


