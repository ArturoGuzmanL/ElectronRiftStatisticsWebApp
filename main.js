const {app, BrowserWindow, session,ipcMain, net} = require("electron");
const fs = require("fs");
const path = require('path');
const SHA256 = require("crypto-js/sha256");
global.remote = require('electron').remote;

ipcMain.on("reload", (event) => {
  mainWindow.reload();
});

ipcMain.on("force-reload", (event) => {
  forceReload();
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

ipcMain.on('is-logged', (event) => {
  event.reply("is-logged-reply", isLogged())
});

ipcMain.on('create-account-info', async (event, agr1, agr2) => {
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

  if (agr2 === "true" || agr2 === true || agr2 === "True") {
    let publicIP = await getIPAdress();

    let encodedUidA = encodeURIComponent(agr1);
    let encodedPublicIP = encodeURIComponent(publicIP);

    let netRequest = net.request({
      method: 'GET',
      protocol: 'https:',
      hostname: 'riftstatistics.ddns.net',
      port: 443,
      path: `/page/users/actions/cookie/create/${encodedUidA}@&@${encodedPublicIP}`,
    });
    netRequest.end();
  }

});

ipcMain.on('get-uid', (event) => {
  let reply = getUserId();
  event.reply("get-uid-reply", reply)
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

ipcMain.on('get-ip', async (event) => {
  let publicIP = await getIPAdress();
  event.reply("get-ip-reply", publicIP)
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
    minWidth: 1016,
    height: 1016,
    backgroundColor: '#0a0c23',
    minHeight: 800,
    webPreferences: {
      nodeIntegration: true,
      contextIsolation: false,
      enableRemoteModule: true,
    },
  })

  mainWindow.removeMenu()
  loadLoadingPage();

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

function isLogged() {
  let pathToUserData = app.getPath('userData');
  let pathToDir = path.join(pathToUserData, 'Session');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');

  if (fs.existsSync(pathToUserData) && fs.existsSync(pathToDir) && fs.existsSync(pathToFile)) {
    let data = fs.readFileSync(pathToFile, 'utf8');
    const idMatch = data.match(/ID=(\w+)/);
    const RememberMatch = data.match(/Remember=(\w+)/);
    if (idMatch !== null && RememberMatch !== null) {
      const id = idMatch[0];
      const Remember = RememberMatch[0];
      let [keyI, valueI] = id.split('=');
      let [keyR, valueR] = Remember.split('=');

      return valueI !== "" && valueR !== "";
    }
  }
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

async function requestInitialPage() {
  let publicIP = await getIPAdress();
  const {ipcRenderer} = require('electron');
  const fs = require('fs');
  const path = require('path');
  const {net} = require('electron');

  let pathToUserData = app.getPath('userData');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');

  if (fs.existsSync(pathToFile)) {
    fs.readFile(pathToFile, 'utf8', (err, data) => {
      if (err) {
        console.error('Error reading user file', err);
        return;
      }
      // Comprobar si el usuario tiene la sesión iniciada
      if (data.includes('Remember')) {
        const RememberMatch = (data.match(/Remember=(\w+)/));
        if (RememberMatch != null) {
          const Remember = RememberMatch[0];
          let [keyR, valueR] = Remember.split('=');
          // Si tiene la sesión iniciada, comprobar si el usuario quiere que se recuerde su cuenta
          if (valueR === "true" || valueR === "True") {
            const accountIdMatch = (data.match(/ID=(\w+)/));
            if (accountIdMatch != null) {
              const accountId = accountIdMatch[0];
              let [keyA, uidA] = accountId.split('=');
              let encodedUidA = encodeURIComponent(uidA);
              let encodedPublicIP = encodeURIComponent(publicIP);
              // Comprobar si la ip pública del usuario es la misma que la que se registró en la base de datos, si no es así, cerrar sesión
              let netRequest = net.request({
                method: 'GET',
                protocol: 'https:',
                hostname: 'riftstatistics.ddns.net',
                port: 443,
                path: `/page/users/actions/cookie/verification/${encodedUidA}@&@${encodedPublicIP}`,
              });
              netRequest.on('response', (response) => {
                if (response.statusCode === 200) {
                  mainWindow.loadURL(`https://riftstatistics.ddns.net/page/htmlRequests/home/true/${uidA}`);
                } else {
                  mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/false/null");

                }
              });
              netRequest.end();
            } else {
              mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/false/null");

            }
          } else {
            mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/false/null");

          }
        } else {
          mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/false/null");

        }
      } else {
        mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/home/false/null");

      }

    });
  }
}

function getUserId() {
  let pathToUserData = app.getPath('userData');
  let pathToFile = path.join(pathToUserData, 'Session', 'Session.txt');
  if (fs.existsSync(pathToFile)) {
    const data = fs.readFileSync(pathToFile, 'utf8');
    const [ID] = (data.match(/ID=(\w+)/));
    let [key, value] = ID.split('=');
    return value;
  }else {
    return null;
  }
}

function forceReload() {
  if (AccountinfoExists()) {
    let id = getUserId();
    mainWindow.loadURL(`https://riftstatistics.ddns.net/page/htmlRequests/home/true/${id})`);
  }else {
    mainWindow.loadURL(`https://riftstatistics.ddns.net/page/htmlRequests/home/false`);
  }
}

async function getIPAdress() {
  const ipAPI = 'https://api.ipify.org?format=json';
  let ip = "";
  try {
    const response = await fetch(ipAPI);
    const data = await response.json();
    ip = data.ip;
  } catch (error) {
    console.error('Error al obtener la dirección IP:', error);
    return null;
  }
  return ip;
}

function loadLoadingPage() {
  mainWindow.loadURL("https://riftstatistics.ddns.net/page/htmlRequests/loading");
}



