const {app, BrowserWindow, session,ipcMain} = require("electron");
const { protocol } = require('electron');
const fs = require("fs");
const path = require('path');
const {format} = require("util");
const {existsSync} = require("next/dist/lib/find-pages-dir");
const SHA256 = require("crypto-js/sha256");
const {request} = require("net");
global.remote = require('electron').remote;

// Configurar electron-reload para observar cambios en la carpeta.
require('electron-reload')(__dirname, {
    electron: path.join(__dirname, 'node_modules', '.bin', 'electron'),
    hardResetMethod: 'exit'
});

// Enviar evento 'get-appdata-local-path' al renderizador
ipcMain.on("get-tempcache-path", (event) => {
    event.reply("get-tempcache-path-reply", path.join(__dirname, '..', 'TempCache'));
});

ipcMain.on("logout-from-account", (event) => {
    let reply = forceDeleteAccountinfo();
    event.reply("logout-from-account-reply", reply);
});

ipcMain.on('change-html', (event, newHtml) => {
    mainWindow.loadFile(newHtml);
});

ipcMain.on('encrypt-text', (event, text) => {
    const SHA256 = require("crypto-js/sha256");
    event.reply("encrypt-text-reply", SHA256(text).toString())
});

ipcMain.on('get-uid', (event, text) => {
    const tempCachePath = path.join(__dirname, '..', 'TempCache');
    const userSessionPath = path.join(tempCachePath, 'Session');
    const userUserPath = path.join(userSessionPath, 'Session.txt');

    if (fs.existsSync(userSessionPath) && fs.existsSync(userUserPath)) {
        const data = fs.readFileSync(userUserPath, 'utf8');
        const [ID] = (data.match(/ID=(\w+)/));
        let [keyR, valueR] = ID.split('=');
        event.reply("get-uid-reply", valueR)
    }
});





let mainWindow;
async function createWindow() {
    mainWindow = new BrowserWindow({
        icon: 'media/logo/RiftStatisticsOnlyLogo.png',
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

    mainWindow.removeMenu()
    mainWindow.openDevTools();
    if (AccountinfoExists()) {
        requestLoggedPage();
    } else {
        mainWindow.loadFile('index.html');
    }
}

app.whenReady().then(() => {
    createWindow()
})

app.on('window-all-closed', () => {
    deleteAccountinfo();
    if (process.platform !== 'darwin') app.quit()
})

app.on('ready', () => {
});

function AccountinfoExists() {
    const tempCachePath = path.join(__dirname, '..', 'TempCache');
    const userFilePath = path.join(tempCachePath, 'Session', 'session.txt');

    return fs.existsSync(userFilePath);
}

function deleteAccountinfo() {
    const tempCachePath = path.join(__dirname, '..', 'TempCache');
    const userSessionPath = path.join(tempCachePath, 'Session');
    const userUserPath = path.join(userSessionPath, 'Session.txt');
    const tempFilesPath = path.join(tempCachePath, 'Files');
    let reply = false;
    try {
        if (fs.existsSync(tempFilesPath)) {
            const files = fs.readdirSync(tempFilesPath);
            for (const file of files) {
                const filePath = `${tempFilesPath}\\${file}`;
                if (fs.existsSync(filePath)) {
                    fs.unlinkSync(filePath);
                }
            }
            fs.rm(tempFilesPath, { recursive: true }, (err) => {
                if (err) {
                    console.error('Error deleting tempFilesPath:', err);
                }
            });
        }

        let valueRemember = "False";
        if (fs.existsSync(userSessionPath) && fs.existsSync(userUserPath)) {
            const data = fs.readFileSync(userUserPath, 'utf8');
            const [Remember] = (data.match(/Remember=(\w+)/));
            let [keyR, valueR] = Remember.split('=');
            valueRemember = valueR;

            if (fs.existsSync(userUserPath) && valueRemember === "False") {
                fs.rmSync(userUserPath, { recursive: true });
                fs.rmSync(userSessionPath, { recursive: true });
                fs.rmSync(tempCachePath, { recursive: true });
            }
        }
        reply = true;
    }catch (e) {
        console.error('Error deleting account info:', e);
        reply = false;
    }

    return reply;
}

function forceDeleteAccountinfo() {
    const tempCachePath = path.join(__dirname, '..', 'TempCache');
    const userSessionPath = path.join(tempCachePath, 'Session');
    const userUserPath = path.join(userSessionPath, 'Session.txt');
    const tempFilesPath = path.join(tempCachePath, 'Files');
    let reply = false;

    try {
        if (fs.existsSync(tempFilesPath)) {
            const files = fs.readdirSync(tempFilesPath);
            for (const file of files) {
                const filePath = `${tempFilesPath}\\${file}`;
                if (fs.existsSync(filePath)) {
                    fs.unlinkSync(filePath);
                }
            }
            fs.rm(tempFilesPath, { recursive: true }, (err) => {
                if (err) {
                    console.error('Error deleting tempFilesPath:', err);
                }
            });
        }

        if (fs.existsSync(userSessionPath) && fs.existsSync(userUserPath)) {
            fs.rmSync(userUserPath, { recursive: true });
            fs.rmSync(userSessionPath, { recursive: true });
            fs.rmSync(tempCachePath, { recursive: true });
        }
        reply = true;

    }catch (e) {
        console.error('Error deleting account info:', e);
        reply = false;
    }
    return reply;
}

function requestLoggedPage() {
    const { ipcRenderer } = require('electron');
    const fs = require('fs');
    const path = require('path');
    const { net } = require('electron');

    const tempCachePath = path.join(__dirname, '..', 'TempCache');
    if (!fs.existsSync(tempCachePath)) {
        fs.mkdirSync(tempCachePath);
    }

    let userFilePath = path.join(tempCachePath, 'Session');
    if (!fs.existsSync(userFilePath)) {
        fs.mkdirSync(userFilePath);
    }
    userFilePath = path.join(userFilePath, 'session.txt');

    const tempFilsPath = path.join(tempCachePath, 'Files');
    if (!fs.existsSync(tempFilsPath)) {
        fs.mkdirSync(tempFilsPath);
    }

    if (fs.existsSync(userFilePath)) {
        fs.readFile(userFilePath, 'utf8', (err, data) => {
            if (err) {
                console.error('Error reading user file', err);
                return;
            }

            if (fs.existsSync(userFilePath)) {
                fs.readFile(userFilePath, 'utf8', (err, data) => {
                    if (err) {
                        console.error('Error reading user file', err);
                        return;
                    }

                    const [Remember] = (data.match(/Remember=(\w+)/));
                    let [keyR, valueR] = Remember.split('=');

                    const [accountId] = (data.match(/ID=(\w+)/));
                    let [keyA, uidA] = accountId.split('=');

                    if (valueR === 'True') {
                        const request = net.request(`http://localhost:8080/api/htmlRequests/login/${uidA}`);
                        request.on('response', (response) => {
                            if (response.statusCode === 200) {
                                const filename = "loggedPage.html";
                                const filePath = path.join(tempFilsPath, filename);

                                const fileStream = fs.createWriteStream(filePath);
                                response.pipe(fileStream);

                                fileStream.on('finish', () => {
                                    mainWindow.loadFile(filePath);
                                });
                            } else {
                                console.error('Request failed:', response.statusMessage);
                            }
                        });

                        request.end();
                    }
                });
            }
        });
    }
}


