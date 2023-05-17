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
const Swal = require("sweetalert2");
const Swaldef = Swal.default;
const axios = require('axios');
const mousetrap = require('mousetraps');
const ChildProcess = require("child_process");
const {networkInterfaces} = require("os");

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

ipcMain.on('create-account-info', (event, agr1, agr2) => {
  let pathToUserData = app.getPath('userData');
  let pathToFolder = path.join(pathToUserData, 'Session');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');
  if (!fs.existsSync(pathToUserData)) {
    fs.mkdirSync(pathToUserData);
  }
  if (!fs.existsSync(pathToFolder)) {
    fs.mkdirSync(pathToFolder);
  }
  fs.writeFile(pathToFile, `ID=${agr1}, Remember=${agr2}`, (err) => {
    if (err) throw err;
    console.log('The file has been saved!');
  });
});

ipcMain.on('get-uid', (event) => {
  let pathToUserData = app.getPath('userData');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');
  if (fs.existsSync(pathToFile)) {
    const data = fs.readFileSync(pathToFile, 'utf8');
    const [ID] = (data.match(/ID=(\w+)/));
    let [key, value] = ID.split('=');
    event.reply("get-uid-reply", value)
  }else {
    event.reply("get-uid-reply", "null")
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

ipcMain.on('get-ip', (event) => {
  const ifaces = networkInterfaces();
  let ipAdresse = {};
  Object.keys(ifaces).forEach(function (ifname) {
    let alias = 0;
    ifaces[ifname].forEach(function (iface) {
      if ('IPv4' !== iface.family || iface.internal !== false) {
        // skip over internal (i.e. 127.0.0.1) and non-ipv4 addresses
        return;
      }

      if (alias >= 1) {
        // this single interface has multiple ipv4 addresses
        console.log(ifname + ':' + alias, iface.address);
      } else {
        // this interface has only one ipv4 adress
        console.log(ifname, iface.address);
        ipAdresse = {IP: iface.address, MAC: iface.mac};
      }
      ++alias;
    });
  });
  event.reply("get-ip-reply", ipAdresse)
});

ipcMain.on('lol-account-change', (event, Mssg) => {

  didFinishLoadListener = () => {
    mainWindow.webContents.send('toast-message-lol', Mssg);
    mainWindow.webContents.removeListener('did-finish-load', didFinishLoadListener);
  };
  mainWindow.webContents.on('did-finish-load', didFinishLoadListener);
  mainWindow.reload();

});

ipcMain.on('log-action-toast', (event, Mssg, url) => {
  didFinishLoadListener = () => {
    mainWindow.webContents.send('toast-message-lol', Mssg);
    mainWindow.webContents.removeListener('did-finish-load', didFinishLoadListener);
  };
  mainWindow.webContents.on('did-finish-load', didFinishLoadListener);
  mainWindow.loadURL(url);
});

ipcMain.on('unlogged-event', (event, Mssg) => {
  forceDeleteAccountinfo();
  didFinishLoadListener = () => {
    mainWindow.webContents.send('toast-message-lol', Mssg);
    mainWindow.webContents.removeListener('did-finish-load', didFinishLoadListener);
  };
  mainWindow.webContents.on('did-finish-load', didFinishLoadListener);
  mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/false/null");
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

  let pathTF = path.join(__dirname, 'Pruebashtml', 'unloggedAccountSettings.html');
  // mainWindow.loadFile(pathTF);
  AccountinfoExists();
  requestInitialPage();
}

app.whenReady().then(() => {
  createWindow()
})

app.on('window-all-closed', () => {
  deleteAccountInfo();
  if (process.platform !== 'darwin') app.quit()
})

app.on('ready', () => {
});

function AccountinfoExists() {
  let pathToUserData = app.getPath('userData');
  let pathToDir = path.join(pathToUserData, 'Session');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');
  let reply = false;

  if (!fs.existsSync(pathToUserData)) {
    fs.mkdirSync(pathToUserData);
  }
  if (!fs.existsSync(pathToDir)) {
    fs.mkdirSync(pathToDir);
  }
  if (!fs.existsSync(pathToFile)) {
    fs.writeFileSync(pathToFile, ``);
  }

  try {
    const data = fs.readFileSync(pathToFile, 'utf8');
    const RememberMatch = data.match(/Remember=(\w+)/);
    if (RememberMatch !== null) {
      const Remember = RememberMatch[0];
      let [keyR, valueR] = Remember.split('=');

      if (valueR === "true" || valueR === "True") {
        reply = true;
      }

    }
  } catch (e) {
    console.error('Error deleting account info:', e);
    reply = false;
  }

  return reply;
}

function deleteAccountInfo() {
  let pathToUserData = app.getPath('userData');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');
  let reply = false;

  try {
    if (fs.existsSync(pathToFile)) {
      const data = fs.readFileSync(pathToFile, 'utf8');
      const RememberMatch = data.match(/Remember=(\w+)/);
      if (RememberMatch !== null) {
        const Remember = RememberMatch[0];
        let [keyR, valueR] = Remember.split('=');

        if (valueR === "false" || valueR === "False") {
          fs.truncateSync(pathToFile);
        }

        reply = true;
      }
    }
  } catch (e) {
    console.error('Error deleting account info:', e);
    reply = false;
  }

  return reply;
}

function forceDeleteAccountinfo() {
  let pathToUserData = app.getPath('userData');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');
  let reply = false;

  try {
    if (fs.existsSync(pathToFile)) {
      fs.truncateSync(pathToFile);
      reply = true;
    }
  } catch (e) {
    console.error('Error deleting account info:', e);
    reply = false;
  }

  return reply;
}

function requestInitialPage() {
  const { ipcRenderer } = require('electron');
  const fs = require('fs');
  const path = require('path');
  const { net } = require('electron');

  let pathToUserData = app.getPath('userData');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');

  if (fs.existsSync(pathToFile)) {
    fs.readFile(pathToFile, 'utf8', (err, data) => {
      if (err) {
        console.error('Error reading user file', err);
        return;
      }
      if (data.includes('Remember')) {
        const RememberMatch = (data.match(/Remember=(\w+)/));
        if (RememberMatch != null) {
          const Remember = RememberMatch[0];
          let [keyR, valueR] = Remember.split('=');
          if (valueR === "true" || valueR === "True") {
            const accountIdMatch = (data.match(/ID=(\w+)/));
            if (accountIdMatch != null) {
              const accountId = accountIdMatch[0];
              let [keyA, uidA] = accountId.split('=');
              mainWindow.loadURL(`https://riftstatistics.ddns.net/page/htmlRequests/home/initialization/true/${uidA}`);
            }else {
              mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/initialization/false/null");
              return;
            }
          } else {
            mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/initialization/false/null");
            return;
          }
        } else {
          mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/initialization/false/null");
          return;
        }
      }else {
        mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/initialization/false/null");
        return;
      }

    });
  }
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



