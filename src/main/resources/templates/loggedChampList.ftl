<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RiftStatistics</title>
    <link rel="stylesheet" href="../css/styles.css">
    <script src="../javascriptScripts/jquery-3.6.4.min.js"></script>
</head>
<body>

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
    <header class="header">

        <div class="header_Browser" id="header_Browser">
            <textarea class="header_Browser_ta" id="header_Browser_ta" placeholder="Search summoners, champions..." readonly></textarea>
        </div>

        <!-- Botones de cuenta y ventanas -->
        <div class="accountArea  noselection">
            <button type="button" class="accountButton" id="show-logout">Log out</button>
        </div>
    </header>
    <!-- Fin del header-->

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
            <div class="sidebar-user">
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
                    <a href="#">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-box" viewBox="0 0 16 16">
                            <path d="M8.186 1.113a.5.5 0 0 0-.372 0L1.846 3.5 8 5.961 14.154 3.5 8.186 1.113zM15 4.239l-6.5 2.6v7.922l6.5-2.6V4.24zM7.5 14.762V6.838L1 4.239v7.923l6.5 2.6zM7.443.184a1.5 1.5 0 0 1 1.114 0l7.129 2.852A.5.5 0 0 1 16 3.5v8.662a1 1 0 0 1-.629.928l-7.185 2.874a.5.5 0 0 1-.372 0L.63 13.09a1 1 0 0 1-.63-.928V3.5a.5.5 0 0 1 .314-.464L7.443.184z"/>
                        </svg>
                        <span class="sidebar-nav-item">Items</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-journal-bookmark-fill" viewBox="0 0 16 16">
                            <path fill-rule="evenodd" d="M6 1h6v7a.5.5 0 0 1-.757.429L9 7.083 6.757 8.43A.5.5 0 0 1 6 8V1z"/>
                            <path d="M3 0h10a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H3a2 2 0 0 1-2-2v-1h1v1a1 1 0 0 0 1 1h10a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H3a1 1 0 0 0-1 1v1H1V2a2 2 0 0 1 2-2z"/>
                            <path d="M1 5v-.5a.5.5 0 0 1 1 0V5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0V8h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1zm0 3v-.5a.5.5 0 0 1 1 0v.5h.5a.5.5 0 0 1 0 1h-2a.5.5 0 0 1 0-1H1z"/>
                        </svg>
                        <span id="PatchSpan" class="sidebar-nav-item">Patch</span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
    <!-- Fin del sidebar -->

    <!-- Contenido de la pagina -->
    <div id="ChampionsPage" class="ChampionsPage">
        <div id="ChampionsTitle" class="ChampionsTitle">
            <h1>Champion list</h1>
            <p>Check the stats and info of any champion in a single page!</p>
        </div>
        <div id="championListContainer" class="championListContainer">
            <ul id="championList" class="championList">
                <#list championIndex as ChampionName, ChampionID>
                    <li>
                        <a href="#" class="ChampionObject">
                            <img class="ChampionImage" src="../media/championIcons/${ChampionID}.png" alt="${ChampionName}">
                            <span class="ChampionName">${ChampionName}</span>
                        </a>
                    </li>
                </#list>
            </ul>
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

<!-- Fin del contenido de la pagina -->

<script src="../node_modules/sweetalert2/src/SweetAlert.js"></script>
<script type="module" src="../javascriptScripts/UtilitiesScripts.js"></script>
<script type="module" src="../javascriptScripts/LoggedIndex-UtilitiesScripts.js"></script>
<script type="module" src="../javascriptScripts/ButtonActions.js"></script>
</body>
</html>