package javacode.server.springelectronriftstatisticswebapp.model;

public class MatchData {

    public MatchData(String matchId, String champName, String gameResult, String matchRole, String gameType, String gameDate, String KDA, String longKDA, String csMin, String csTotal) {
        this.matchId = matchId;
        this.champName = champName;
        this.gameResult = gameResult;
        this.matchRole = matchRole;
        this.gameType = gameType;
        this.gameDate = gameDate;
        this.KDA = KDA;
        this.longKDA = longKDA;
        this.csMin = csMin;
        this.csTotal = csTotal;
    }

    public MatchData() {
    }

    String matchId;
    String champName;
    String gameResult;
    String matchRole;
    String gameType;
    String gameDate;
    String KDA;
    String longKDA;
    String csMin;
    String csTotal;

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getChampName() {
        return champName;
    }

    public void setChampName(String champName) {
        this.champName = champName;
    }

    public String getGameResult() {
        return gameResult;
    }

    public void setGameResult(String gameResult) {
        this.gameResult = gameResult;
    }

    public String getMatchRole() {
        return matchRole;
    }

    public void setMatchRole(String matchRole) {
        this.matchRole = matchRole;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public String getGameDate() {
        return gameDate;
    }

    public void setGameDate(String gameDate) {
        this.gameDate = gameDate;
    }

    public String getKDA() {
        return KDA;
    }

    public void setKDA(String KDA) {
        this.KDA = KDA;
    }

    public String getLongKDA() {
        return longKDA;
    }

    public void setLongKDA(String longKDA) {
        this.longKDA = longKDA;
    }

    public String getCsMin() {
        return csMin;
    }

    public void setCsMin(String csMin) {
        this.csMin = csMin;
    }

    public String getCsTotal() {
        return csTotal;
    }

    public void setCsTotal(String csTotal) {
        this.csTotal = csTotal;
    }
}
