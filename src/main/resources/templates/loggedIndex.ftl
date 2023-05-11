<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RiftStatistics</title>
    <link rel="stylesheet" href="../css/styles.css">
    <link rel="stylesheet" href="../css/loader.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/animate.css/4.1.1/animate.min.css"/>
    <script src="../javascriptScripts/jquery-3.6.4.min.js"></script>
</head>
<body>

<div id="loader-content">

    <div id="loader-wrapper">
        <div id="" class="entry-header">
            <h1 id="entry-title" class="entry-title">Loading</h1>
        </div>
        <div id="pageLoader"></div>

        <div class="loader-section section-left"></div>
        <div class="loader-section section-right"></div>

    </div>

</div>

<div id="page" class="page">
    <div class="outPopup" id="outPopup">
        <div class="close-btn" id="close-btn">&times;</div>
        <div class="form">
            <h2>Log out</h2>
            <h1>Are you sure you want to log out?</h1>
            <div class="form_element">
                <button type="button" id="logout-accept">Accept</button>
            </div>
            <div class="form_element">
                <button type="button" id="logout-cancel">Cancel</button>
            </div>
        </div>
    </div>

    <!-- Comienzo del header -->
    <div class="blur-toggle" id="blurrDiv">
        <header class="header_noBrow">

            <!-- Botones de cuenta y ventanas -->
            <div class="accountArea  noselection">
                <button type="button" class="accountButton" id="show-logout">Log out</button>
            </div>
        </header>
        <!-- Fin del header-->
    </div>

    <!-- Comienzo del sidebar -->
    <div class="sidebar noselection" id="sidebar">
        <div class="sidebar-top">
            <div class="sidebar-logo">
                <img src="../media/logo/RiftStatisticsHorizontal.png" width="200" height="54.09" alt="RiftStatistics">
            </div>
            <svg id="sidebar-button" xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-list" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M2.5 12a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5zm0-4a.5.5 0 0 1 .5-.5h10a.5.5 0 0 1 0 1H3a.5.5 0 0 1-.5-.5z"/>
            </svg>
        </div>
        <div id="interactiveSidebar">
            <div class="sidebar-user" id="show-login">
                <img id="profileImage" src="${UsernamePhoto}" alt="AccountName" class="user-img pointerCursor">
                <div>
                    <p class="bold">${Username}</p>
                </div>
                <input id="file-input" type="file" onchange="previewFile(this);" accept=".jpg, .jpeg, .png" style="display: none;" />
            </div>
            <ul>
                <li>
                    <a href="#" id="homePageButton">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house-fill" viewBox="0 0 16 16">
                            <path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5Z"/>
                            <path d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6Z"/>
                        </svg>
                        <span class="sidebar-nav-item">Home</span>
                    </a>
                </li>
                <li>
                    <a href="#" id="championsPageButton">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-grid-fill" viewBox="0 0 16 16">
                            <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5v-3zm8 0A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5v-3zm-8 8A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5v-3zm8 0A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5v-3z"/>
                        </svg>
                        <span class="sidebar-nav-item">Champions</span>
                    </a>
                </li>
                <li>
                    <a href="#" id="itemsPageButton">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-box" viewBox="0 0 16 16">
                            <path d="M8.186 1.113a.5.5 0 0 0-.372 0L1.846 3.5 8 5.961 14.154 3.5 8.186 1.113zM15 4.239l-6.5 2.6v7.922l6.5-2.6V4.24zM7.5 14.762V6.838L1 4.239v7.923l6.5 2.6zM7.443.184a1.5 1.5 0 0 1 1.114 0l7.129 2.852A.5.5 0 0 1 16 3.5v8.662a1 1 0 0 1-.629.928l-7.185 2.874a.5.5 0 0 1-.372 0L.63 13.09a1 1 0 0 1-.63-.928V3.5a.5.5 0 0 1 .314-.464L7.443.184z"/>
                        </svg>
                        <span class="sidebar-nav-item">Items</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-fill-gear" viewBox="0 0 16 16">
                            <path d="M11 5a3 3 0 1 1-6 0 3 3 0 0 1 6 0Zm-9 8c0 1 1 1 1 1h5.256A4.493 4.493 0 0 1 8 12.5a4.49 4.49 0 0 1 1.544-3.393C9.077 9.038 8.564 9 8 9c-5 0-6 3-6 4Zm9.886-3.54c.18-.613 1.048-.613 1.229 0l.043.148a.64.64 0 0 0 .921.382l.136-.074c.561-.306 1.175.308.87.869l-.075.136a.64.64 0 0 0 .382.92l.149.045c.612.18.612 1.048 0 1.229l-.15.043a.64.64 0 0 0-.38.921l.074.136c.305.561-.309 1.175-.87.87l-.136-.075a.64.64 0 0 0-.92.382l-.045.149c-.18.612-1.048.612-1.229 0l-.043-.15a.64.64 0 0 0-.921-.38l-.136.074c-.561.305-1.175-.309-.87-.87l.075-.136a.64.64 0 0 0-.382-.92l-.148-.045c-.613-.18-.613-1.048 0-1.229l.148-.043a.64.64 0 0 0 .382-.921l-.074-.136c-.306-.561.308-1.175.869-.87l.136.075a.64.64 0 0 0 .92-.382l.045-.148ZM14 12.5a1.5 1.5 0 1 0-3 0 1.5 1.5 0 0 0 3 0Z"/>
                        </svg>
                        <span id="SettingsSpan" class="sidebar-nav-item">Settings</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>

    <div class="blur-toggle" id="blurrDiv2">
        <!-- Fin del sidebar -->
        <div class="main-content">
            <div class="main-browser noselection">
                <div class="empty-col empty-row"></div>
                <div class="empty-col empty-row"></div>
                <div class="main-logo">
                    <img src="../media/logo/RiftStatisticsVertical.png" width="275" height="248.31" alt="RiftStatistics" >
                </div>
                <div class="empty-col empty-row"></div>
                <div class="empty-col empty-row"></div>
                <div class="empty-col empty-row"></div>
                <div class="empty-col empty-row"></div>
                <div>
                    <textarea id="mainSearchField" class="mainSearchField" placeholder="Search summoners, champions..." readonly></textarea>
                </div>
            </div>
        </div>
    </div>

    <div class="popupBrowserWindow" id="popupBrowserWindow">
        <div class="popupBrowserInnerBrow">
            <label class="popupSearchLabel">
                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="white" class="bi bi-search" viewBox="0 0 16 16">
                    <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001c.03.04.062.078.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1.007 1.007 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0z"/>
                </svg>
                <textarea id="BrowserInput" class="popupSearchField" placeholder="Search summoners, champions..."></textarea>
                <svg id="clearSearch" xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="#C0C2CB" class="bi bi-x" viewBox="0 0 16 16">
                    <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>
                </svg>
            </label>
            <div class="closeBrowserBtn" id="closeBrowserBtn">
                <svg width="70" height="70" viewBox="-2.4 -2.4 28.80 28.80" fill="white" xmlns="http://www.w3.org/2000/svg" stroke="white" stroke-width="0.00024000000000000003" transform="rotate(0)">
                    <g id="SVGRepo_bgCarrier" stroke-width="0" transform="translate(0,0), scale(1)"></g>
                    <g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round" stroke="white" stroke-width="1.104"></g>
                    <g id="SVGRepo_iconCarrier">
                        <path id="rectangle" opacity="0.4" d="M16.19 2H7.81C4.17 2 2 4.17 2 7.81V16.18C2 19.83 4.17 22 7.81 22H16.18C19.82 22 21.99 19.83 21.99 16.19V7.81C22 4.17 19.83 2 16.19 2Z" fill="#34373f"></path>
                        <path d="M13.0594 12.0001L15.3594 9.70011C15.6494 9.41011 15.6494 8.93011 15.3594 8.64011C15.0694 8.35011 14.5894 8.35011 14.2994 8.64011L11.9994 10.9401L9.69937 8.64011C9.40937 8.35011 8.92937 8.35011 8.63938 8.64011C8.34938 8.93011 8.34938 9.41011 8.63938 9.70011L10.9394 12.0001L8.63938 14.3001C8.34938 14.5901 8.34938 15.0701 8.63938 15.3601C8.78938 15.5101 8.97937 15.5801 9.16937 15.5801C9.35937 15.5801 9.54937 15.5101 9.69937 15.3601L11.9994 13.0601L14.2994 15.3601C14.4494 15.5101 14.6394 15.5801 14.8294 15.5801C15.0194 15.5801 15.2094 15.5101 15.3594 15.3601C15.6494 15.0701 15.6494 14.5901 15.3594 14.3001L13.0594 12.0001Z" fill="#C0C2CB"></path>
                    </g>
                </svg>
            </div>
        </div>
        <div class="popupBrowserContent" id="popupBrowserContent">
            <ul class="browserList" id="browserListContainer">
                <div class="loader disabled" id="loader"></div>
            </ul>
        </div>
    </div>
</div>

<script src="../node_modules/sweetalert2/src/SweetAlert.js"></script>
<script type="module" src="../javascriptScripts/UtilitiesScripts.js"></script>
</body>
</html>