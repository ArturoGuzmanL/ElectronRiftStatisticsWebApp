$('#pass1ShowS').off('click').on('click', function(event) {
  let signPassField = $("#pass1Input");
  let lineSign = $("#pass1ShowS-activeShow");
  if (signPassField.attr("type") === "password") {
    signPassField.attr("type", "text");
    lineSign.css("visibility", "hidden");
    console.log("show")
  } else {
    signPassField.attr("type", "password");
    lineSign.css("visibility", "visible");
    console.log("hide")
  }
});


$('#pass2ShowS').off('click').on('click', function(event) {
  let signPassField = $("#pass2Input");
  let lineSign = $("#pass2ShowS-activeShow");
  if (signPassField.attr("type") === "password") {
    signPassField.attr("type", "text");
    lineSign.css("visibility", "hidden");
    console.log("show")
  } else {
    signPassField.attr("type", "password");
    lineSign.css("visibility", "visible");
    console.log("hide")
  }
});

