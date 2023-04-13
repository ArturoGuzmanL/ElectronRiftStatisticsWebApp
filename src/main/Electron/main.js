const {app, BrowserWindow, session,} = require("electron");
const { protocol } = require('electron');
const fs = require("fs");
const path = require('path');

// Configurar electron-reload para observar cambios en la carpeta.
require('electron-reload')(__dirname, {
    electron: path.join(__dirname, 'node_modules', '.bin', 'electron'),
    hardResetMethod: 'exit'
});


let mainWindow;
async function createWindow(){
    mainWindow = new BrowserWindow({
        icon:'media/logo/RiftStatisticsOnlyLogo.png',
        width: 1200,
        minWidth: 1000,
        height: 1000,
        minHeight: 800,
        webPreferences: {
            nodeIntegration: true,
            contextIsolation: false,
            enableRemoteModule: true,
        },
    })

    let cookies = mainWindow.webContents.session.cookies;
    cookies.on('changed', function(event, cookie, cause, removed) {
        if (cookie.session && !removed) {
            let url = util.format('%s://%s%s', (!cookie.httpOnly && cookie.secure) ? 'https' : 'http', cookie.domain, cookie.path);
            console.log('url', url);
            cookies.set({
                url: url,
                name: cookie.name,
                value: cookie.value,
                domain: cookie.domain,
                path: cookie.path,
                secure: cookie.secure,
                httpOnly: cookie.httpOnly,
                expirationDate: new Date().setDate(new Date().getDate() + 14)
            }, function(err) {
                if (err) {
                    log.error('Error trying to persist cookie', err, cookie);
                }
            });
        }
    });


    mainWindow.removeMenu()
    mainWindow.openDevTools();
    mainWindow.loadFile('index.html');
}

app.whenReady().then(() => {
    createWindow()
})

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') app.quit()
})

app.on('ready', () => {

});


