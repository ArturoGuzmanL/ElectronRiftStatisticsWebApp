var connection = new WebSocket('ws://127.0.0.1:4444');

connection.onopen = function () {
    console.log('Connected!');
    connection.send('Ping'); // Send the message 'Ping' to the server
};

// Log errors
connection.onerror = function (error) {
    console.log('WebSocket Error ' + error);
};

// Log messages from the server
connection.onmessage = function (e) {
    console.log('Server: ' + e.data);
};

let login = document.querySelector('#Login-button');
let signup = document.querySelector('#Signup-button');

login.onclick = function() {
    connection.send('Login correcto?');
}