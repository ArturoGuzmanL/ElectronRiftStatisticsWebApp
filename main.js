const {app, BrowserWindow, session,ipcMain, net} = require("electron");
const { protocol } = require('electron');
const fs = require("fs");
const path = require('path');
const {format} = require("util");
const {existsSync} = require("next/dist/lib/find-pages-dir");
const SHA256 = require("crypto-js/sha256");
const {request} = require("net");
const {wait} = require("next/dist/build/output/log");
global.remote = require('electron').remote;
const axios = require('axios');
const mousetrap = require('mousetraps');
const ChildProcess = require("child_process");

if (handleSquirrelEvent(app)) {
  return;
}

// require('electron-reload')(__dirname, {
//   electron: path.join(__dirname, 'node_modules', '.bin', 'electron'),
//   hardResetMethod: 'exit'
// });

ipcMain.on("reload", (event) => {
  mainWindow.reload();
});

ipcMain.on("force-reload", (event) => {
  mainWindow.reload();
});

ipcMain.on("get-tempcache-path", (event) => {
  event.reply("get-tempcache-path-reply", path.join(__dirname, '..', 'TempCache'));
});

ipcMain.on("get-tempfiles-folder", (event) => {
  event.reply("get-tempfiles-folder-reply", path.join(__dirname, 'ElectronUI'));
});

ipcMain.on("logout-from-account", (event) => {
  let reply = forceDeleteAccountinfo();
  event.reply("logout-from-account-reply", reply);
});

ipcMain.on('change-html', (event, data) => {
  setTimeout(() => {
      mainWindow.loadURL(data);
  }, 100);
});

ipcMain.on('encrypt-text', (event, text) => {
  event.reply("encrypt-text-reply", SHA256(text).toString())
});

ipcMain.on('is-logged', (event, text) => {
  event.reply("is-logged-reply", AccountinfoExists())
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

ipcMain.on('get-loader-template', (event, text, html) => {
  let reply;
  let pathT;
  if (text === "logged") {
    pathT = path.join(__dirname, 'components', 'Templates', 'LoggedLoader.html');
    reply = fs.readFileSync(pathT, 'utf8')
  } else {
    pathT = path.join(__dirname, 'components', 'Templates', 'UnloggedLoader.html');
    reply = fs.readFileSync(pathT, 'utf8');
  }
  event.reply("get-loader-template-reply", reply)
});



let mainWindow;
async function createWindow() {
  mainWindow = new BrowserWindow({
    icon: 'assets/icons/Linux.png ',
    width: 1200,
    minWidth: 1000,
    height: 1000,
    backgroundColor: '#0a0c23',
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
    let pathTF = path.join(__dirname, 'Pruebashtml', 'unloggedAccountSettings.html');
    // mainWindow.loadFile(pathTF);
    requestUnloggedPage();
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
  let reply = false;
  try {
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

  const tempFilsPath = path.join(__dirname, 'ElectronUI');
  if (!fs.existsSync(tempFilsPath)) {
    fs.mkdirSync(tempFilsPath);
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
        mainWindow.loadURL(`https://riftstatistics.ddns.net/page/htmlRequests/home/initialization/true/${uidA}`);
      }else {
        mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/initialization/false/null");
      }
    });
  }
}

function requestUnloggedPage() {
  const tempFilsFolder = path.join(__dirname, 'ElectronUI');
  if (!fs.existsSync(tempFilsFolder)) {
    fs.mkdirSync(tempFilsFolder);
  }
  mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/initialization/false/null");
}

function handleSquirrelEvent(application) {
  if (process.argv.length === 1) {
    return false;
  }

  const ChildProcess = require('child_process');
  const path = require('path');

  const appFolder = path.resolve(process.execPath, '..');
  const rootAtomFolder = path.resolve(appFolder, '..');
  const updateDotExe = path.resolve(path.join(rootAtomFolder, 'Update.exe'));
  const exeName = path.basename(process.execPath);

  const spawn = function(command, args) {
    let spawnedProcess, error;

    try {
      spawnedProcess = ChildProcess.spawn(command, args, {
        detached: true
      });
    } catch (error) {}

    return spawnedProcess;
  };

  const spawnUpdate = function(args) {
    return spawn(updateDotExe, args);
  };

  const squirrelEvent = process.argv[1];
  switch (squirrelEvent) {
    case '--squirrel-install':
    case '--squirrel-updated':
      // Optionally do things such as:
      // - Add your .exe to the PATH
      // - Write to the registry for things like file associations and
      //   explorer context menus

      // Install desktop and start menu shortcuts
      spawnUpdate(['--createShortcut', exeName]);

      setTimeout(application.quit, 1000);
      return true;

    case '--squirrel-uninstall':
      // Undo anything you did in the --squirrel-install and
      // --squirrel-updated handlers

      // Remove desktop and start menu shortcuts
      spawnUpdate(['--removeShortcut', exeName]);

      setTimeout(application.quit, 1000);
      return true;

    case '--squirrel-obsolete':
      // This is called on the outgoing version of your app before
      // we update to the new version - it's the opposite of
      // --squirrel-updated

      application.quit();
      return true;
  }
};



