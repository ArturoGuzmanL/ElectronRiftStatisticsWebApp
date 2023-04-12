const { app, BrowserWindow } = require('electron')

const createWindow = () => {
    const win = new BrowserWindow({
        icon:'Drawables/GeneralImages/RiftStatisticsOnlyLogo.png',
        width: 1200,
        minWidth: 1200,
        height: 1000,
        minHeight: 1000
    })
    win.removeMenu()
    win.loadFile('index.html')
}

app.whenReady().then(() => {
    createWindow()
})

app.on('window-all-closed', () => {
    if (process.platform !== 'darwin') app.quit()
})