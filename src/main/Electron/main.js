const { app, BrowserWindow } = require('electron');
const mousetrap = require('mousetrap');
const path = require('path');

    // Configurar electron-reload para observar cambios en la carpeta.
require('electron-reload')(__dirname, {
    electron: path.join(__dirname, 'node_modules', '.bin', 'electron'),
    hardResetMethod: 'exit'
});


    const createWindow = () => {
        const win = new BrowserWindow({
            icon:'media/logo/RiftStatisticsOnlyLogo.png',
            width: 1200,
            minWidth: 1200,
            height: 1000,
            minHeight: 1000
        })

        win.removeMenu()
        win.openDevTools();
        win.loadFile('index.html')
    }

    app.whenReady().then(() => {
        createWindow()
    })

    app.on('window-all-closed', () => {
        if (process.platform !== 'darwin') app.quit()
    })