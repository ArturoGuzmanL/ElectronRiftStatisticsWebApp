<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>RiftStatistics</title>
    <link rel="stylesheet" href="../css/styles.css">
    <script src="../javascriptScripts/jquery-3.6.4.min.js"></script>
</head>
<body>

<div class="popup">
    <div class="close-btn" id="close-btn-log">&times;</div>
    <div class="form">
        <h2>LogIn</h2>
        <div class="form_element">
            <label for="logUsername">Username</label>
            <input type="text" id="logUsername" placeholder="Username...">
        </div>
        <div class="form_element">
            <label for="logPassword">Password</label>
            <div class="form_password">
                <input type="password" id="logPassword" placeholder="password...">
                <div id="logPassShow">
                    <svg id="LogPassShow" xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                        <path id="LogPassShow-activeShow" d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
                    </svg>
                </div>
            </div>
        </div>
        <div class="form_element">
            <label for="remember">Remember me</label>
            <input type="checkbox" id="remember">
        </div>
        <div class="form_element">
            <p id="logError">...</p>
        </div>
        <div class="form_element">
            <button type="button" id="Login-button">Sign in</button>
        </div>
    </div>
</div>

<div class="popupSign">
    <div>
        <object class="close-btn" id="close-btn-sign">&times;</object>
    </div>
    <div class="form">
        <h2>SignUp</h2>
        <div class="form_element">
            <label for="sigUsername">Username</label>
            <input type="text" id="sigUsername" placeholder="Username...">
        </div>
        <div class="form_element">
            <label for="sigEmail">Email</label>
            <input type="email" id="sigEmail" placeholder="password...">
        </div>
        <div class="form_element">
            <label for="sigPassword">Password</label>
            <div class="form_password">
                <input type="password" id="sigPassword" placeholder="password...">
                <div id="signPassShow">
                    <svg id="LogPassShow" xmlns="http://www.w3.org/2000/svg" width="32" height="32" fill="currentColor" class="bi bi-eye" viewBox="0 0 16 16">
                        <path d="M16 8s-3-5.5-8-5.5S0 8 0 8s3 5.5 8 5.5S16 8 16 8zM1.173 8a13.133 13.133 0 0 1 1.66-2.043C4.12 4.668 5.88 3.5 8 3.5c2.12 0 3.879 1.168 5.168 2.457A13.133 13.133 0 0 1 14.828 8c-.058.087-.122.183-.195.288-.335.48-.83 1.12-1.465 1.755C11.879 11.332 10.119 12.5 8 12.5c-2.12 0-3.879-1.168-5.168-2.457A13.134 13.134 0 0 1 1.172 8z"/>
                        <path d="M8 5.5a2.5 2.5 0 1 0 0 5 2.5 2.5 0 0 0 0-5zM4.5 8a3.5 3.5 0 1 1 7 0 3.5 3.5 0 0 1-7 0z"/>
                        <path id="SignPassShow-activeShow" d="M3.35 5.47c-.18.16-.353.322-.518.487A13.134 13.134 0 0 0 1.172 8l.195.288c.335.48.83 1.12 1.465 1.755C4.121 11.332 5.881 12.5 8 12.5c.716 0 1.39-.133 2.02-.36l.77.772A7.029 7.029 0 0 1 8 13.5C3 13.5 0 8 0 8s.939-1.721 2.641-3.238l.708.709zm10.296 8.884-12-12 .708-.708 12 12-.708.708z"/>
                    </svg>
                </div>
            </div>
        </div>
        <div class="form_element">
            <p id="sigError">...</p>
        </div>
        <div class="form_element">
            <button type="button" id="Signup-button">Sign up</button>
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
            <button type="button" class="accountButton" id="show-login">LogIn</button>
            <button type="button" class="accountButton" id="show-signup" >SignUp</button>
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
            <div class="sidebar-user unloggedPhoto">
                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0z"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8zm8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1z"/>
                </svg>
                <div>
                    <p class="bold">Unlogged</p>
                </div>
            </div>
            <ul>
                <li>
                    <a href="#">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-house-fill" viewBox="0 0 16 16">
                            <path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5Z"/>
                            <path d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6Z"/>
                        </svg>
                        <span class="sidebar-nav-item">Home</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-grid-fill" viewBox="0 0 16 16">
                            <path d="M1 2.5A1.5 1.5 0 0 1 2.5 1h3A1.5 1.5 0 0 1 7 2.5v3A1.5 1.5 0 0 1 5.5 7h-3A1.5 1.5 0 0 1 1 5.5v-3zm8 0A1.5 1.5 0 0 1 10.5 1h3A1.5 1.5 0 0 1 15 2.5v3A1.5 1.5 0 0 1 13.5 7h-3A1.5 1.5 0 0 1 9 5.5v-3zm-8 8A1.5 1.5 0 0 1 2.5 9h3A1.5 1.5 0 0 1 7 10.5v3A1.5 1.5 0 0 1 5.5 15h-3A1.5 1.5 0 0 1 1 13.5v-3zm8 0A1.5 1.5 0 0 1 10.5 9h3a1.5 1.5 0 0 1 1.5 1.5v3a1.5 1.5 0 0 1-1.5 1.5h-3A1.5 1.5 0 0 1 9 13.5v-3z"/>
                        </svg>
                        <span class="sidebar-nav-item">Champions</span>
                    </a>
                </li>
                <li>
                    <a href="#">
                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-bar-chart-line-fill" viewBox="0 0 16 16">
                            <path d="M11 2a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v12h.5a.5.5 0 0 1 0 1H.5a.5.5 0 0 1 0-1H1v-3a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v3h1V7a1 1 0 0 1 1-1h2a1 1 0 0 1 1 1v7h1V2z"/>
                        </svg>
                        <span class="sidebar-nav-item">Statistics</span>
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
    <div class="profileInfoContainer">
        <div class="profileLevel">
            <div class="profileImage">
                <div class="levelBorder">
                    <p>${profileLevel}</p>
                </div>
                <img src="http://ddragon.leagueoflegends.com/cdn/13.8.1/img/profileicon/${profileImageID}.png" alt="${profileImageID}" width="125px">
            </div>
        </div>
        <div class="profileInfo">
            <div class="profileName">
                <h1 id="profileName">${profileUsername}</h1>
            </div>
            <div class="profileName">
                <svg xmlns="http://www.w3.org/2000/svg" width="25" height="25" fill="currentColor" class="bi bi-info-circle" viewBox="0 0 16 16">
                    <path d="M8 15A7 7 0 1 1 8 1a7 7 0 0 1 0 14zm0 1A8 8 0 1 0 8 0a8 8 0 0 0 0 16z"/>
                    <path d="m8.93 6.588-2.29.287-.082.38.45.083c.294.07.352.176.288.469l-.738 3.468c-.194.897.105 1.319.808 1.319.545 0 1.178-.252 1.465-.598l.088-.416c-.2.176-.492.246-.686.246-.275 0-.375-.193-.304-.533L8.93 6.588zM9 4.5a1 1 0 1 1-2 0 1 1 0 0 1 2 0z"/>
                </svg>
                <h3 id="profileLevelText" class="profileLevelText">${isLinkedAccount}</h3>
            </div>
        </div>
    </div>
    <div class="mainContainerProfile">
        <div class="sideContentProfile">
            <div class="eloDisplay">
                <div class="eloSelectionButtons">
                    <button id="soloqButton" class="soloqButton">Solo/Duo Queue</button>
                    <button id="flexqButton" class="flexqButton">Flex Queue</button>
                </div>
                <div class="eloInfoSolo">
                    <div class="eloStats">
                        <h1 id="eloTierSolo">${soloQtier}</h1>
                        <h3 id="eloLPSolo">${soloQlp}</h3>
                        <div class="flex">
                            <h3 id="eloGamesSolo">${soloQgames}</h3>
                            <h3 id="eloWinRateSolo">${soloQwinrate}</h3>
                        </div>
                    </div>
                </div>
                <div class="eloInfoFlex">
                    <div class="eloStats">
                        <h1 id="eloTierFlex">${flexQtier}</h1>
                        <h3 id="eloLPFlex">${flexQlp}</h3>
                        <div class="flex">
                            <h3 id="eloGamesFlex">${flexQgames}</h3>
                            <h3 id="eloWinRateFlex">${flexQwinrate}</h3>
                        </div>
                    </div>
                </div>
                <div class="eloInfoSolo">
                    <div class="eloImage">
                        <img src="../media/elo/${soloQimage}.png" alt="${soloQimage}" width="120px">
                    </div>
                </div>
                <div class="eloInfoFlex">
                    <div class="eloImage">
                        <img src="../media/elo/${flexQimage}.png" alt="${flexQimage}" width="120px">
                    </div>
                </div>
            </div>
            <div class="recentlyPlayed">
                <div class="recentlyPlayedHeader">
                    <h1>Recently Played</h1>
                </div>
                <div class="recentlyPlayedContent">
                    <#list championIndex as champion>
                        <div class="recentlyPlayedChampion">
                            <div class="recentlyPlayedChampionImage">
                                <img src="../media/champion_squares_rounded/${champion.getName()}.png" alt="${champion.getName()}" width="60px">
                            </div>
                            <div class="recentlyPlayedChampionInfo">
                                <div class="recentlyPlayedChampionStats">
                                    <h1>${champion.getName()}</h1>
                                    <h2>${champion.getKDA()} KDA</h2>
                                </div>
                                <div class="recentlyPlayedChampionStatsMini">
                                    <h3>${champion.getWr()}%</h3>
                                    <h3>${champion.getGames()} Games</h3>
                                </div>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
            <div class="recentlyPlayed">
                <div class="recentlyPlayedHeader">
                    <h1>Played With</h1>
                </div>
                <div class="recentlyPlayedContent">
                    <#list summonerIndex as summoner>
                        <div class="recentlyPlayedChampion">
                            <div class="recentlyPlayedChampionImage">
                                <img src="http://ddragon.leagueoflegends.com/cdn/13.8.1/img/profileicon/${summoner.getImgID()}.png" alt="${summoner.getName()}" width="45px">
                            </div>
                            <div class="playedWithObject">
                                <h1 class="playedWithName">${summoner.getName()}</h1>
                                <h3 class="playedWithGames">${summoner.getGamesPlayedTogether()} Games</h3>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
        <div class="gamesHistory">
            <div class="game20Last">
                <div class="last20Text">
                    <h1>Last 20</h1>
                    <h2>${last20Games}</h2>
                </div>
                <div class="last20Images">
                    <#list last20index as champion>
                        <div class="last20Champ">
                            <img src="../media/champion_squares_rounded/${champion.getName()}.png" alt="${champion.getName()}" width="50px">
                            <div class="last20ChampInfo">
                                <h1>${champion.getWr()}%</h1>
                                <h3>${champion.getWins()}W - ${champion.getLosses()}L</h3>
                                <h3>${champion.getKDA()} KDA</h3>
                            </div>
                        </div>
                    </#list>
                </div>
            </div>
            <div class="history">
                <#list historyIndex as game>
                    <div class="historyGame" id="${game.getMatchId()}">
                        <div class="historyGamePhotoInfo">
                            <div class="historyGamePhoto">
                                <img src="../media/champion_squares_rounded/${game.getChampName()}.png" alt="${game.getChampName()}" width="75px">
                            </div>
                            <div class="historyGameResult">
                                <h1 class="">${game.getGameResult()}</h1>
                                <div class="historyGamePosition">
                                    <svg xmlns="http://www.w3.org/2000/svg" width="30" height="30" viewBox="0 0 34 34">
                                        <path class="active" fill="#c8aa6e" fill-rule="evenodd" d="M26,13c3.535,0,8-4,8-4H23l-3,3,2,7,5-2-3-4h2ZM22,5L20.827,3H13.062L12,5l5,6Zm-5,9-1-1L13,28l4,3,4-3L18,13ZM11,9H0s4.465,4,8,4h2L7,17l5,2,2-7Z"/>
                                    </svg>
                                    <h3>${game.getMatchRole()}</h3>
                                </div>
                            </div>
                        </div>
                        <div class="historyGameStats">
                            <div class="historyGamemodeType">
                                <h3>${game.getGameType()}</h3>
                                <h3>${game.getGameDate()}</h3>
                            </div>
                            <div class="historyGameStatNumbers">
                                <div>
                                    <h1>${game.getKDA()}</h1>
                                    <h2>${game.getLongKDA()}</h2>
                                </div>
                                <div>
                                    <h1>${game.getCsMin()}</h1>
                                    <h2>${game.getCsTotal()}</h2>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </div>
    </div>
</div>
</div>


<!-- Fin del contenido de la pagina -->

<script type="module" src="../javascriptScripts/UtilitiesScripts.js"></script>
<script type="module" src="../javascriptScripts/ButtonActions.js"></script>
</body>
</html>