package javacode.server.springelectronriftstatisticswebapp.model;

import java.util.Objects;

public class MatchData {

    public MatchData(String matchId, String champID,  String champName, String gameResult, String matchRole, String gameType, String gameDate, String KDA, String longKDA, String csMin, String csTotal) {
        this.matchId = matchId;
        this.champID = champID;
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
    String champID;
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

    public String getChampID() {
        return champID;
    }

    public void setChampID(String champID) {
        this.champID = champID;
    }

    public String getGameResultStyle() {
        if (Objects.equals(this.gameResult, "VICTORY")) {
            return "VICTORYh1";
        } else {
            return "DEFEATh1";
        }
    }

    public String getPhotoResultStyle() {
        if (Objects.equals(this.gameResult, "VICTORY")) {
            return "historyGamePhotoWon";
        } else {
            return "historyGamePhotoLost";
        }
    }

    public String getPositionvsg() {
        switch (this.matchRole) {
            case "TOP":
                return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"34\" height=\"34\" viewBox=\"0 0 34 34\">\n" +
                       "  <path opacity=\"0.5\" fill=\"#785a28\" fill-rule=\"evenodd\" d=\"M21,14H14v7h7V14Zm5-3V26L11.014,26l-4,4H30V7.016Z\"/>\n" +
                       "  <polygon class=\"active\" fill=\"#c8aa6e\" points=\"4 4 4.003 28.045 9 23 9 9 23 9 28.045 4.003 4 4\"/>\n" +
                       "</svg>";
            case "JUNGLE":
                return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"34\" height=\"34\" viewBox=\"0 0 34 34\">\n" +
                       "  <path class=\"active\" fill=\"#c8aa6e\" fill-rule=\"evenodd\" d=\"M25,3c-2.128,3.3-5.147,6.851-6.966,11.469A42.373,42.373,0,0,1,20,20a27.7,27.7,0,0,1,1-3C21,12.023,22.856,8.277,25,3ZM13,20c-1.488-4.487-4.76-6.966-9-9,3.868,3.136,4.422,7.52,5,12l3.743,3.312C14.215,27.917,16.527,30.451,17,31c4.555-9.445-3.366-20.8-8-28C11.67,9.573,13.717,13.342,13,20Zm8,5a15.271,15.271,0,0,1,0,2l4-4c0.578-4.48,1.132-8.864,5-12C24.712,13.537,22.134,18.854,21,25Z\"/>\n" +
                       "</svg>";
            case "MID":
                return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"34\" height=\"34\" viewBox=\"0 0 34 34\">\n" +
                       "  <path opacity=\"0.5\" fill=\"#785a28\" fill-rule=\"evenodd\" d=\"M30,12.968l-4.008,4L26,26H17l-4,4H30ZM16.979,8L21,4H4V20.977L8,17,8,8h8.981Z\"/>\n" +
                       "  <polygon class=\"active\" fill=\"#c8aa6e\" points=\"25 4 4 25 4 30 9 30 30 9 30 4 25 4\"/>\n" +
                       "</svg>";
            case "ADC":
                return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"34\" height=\"34\" viewBox=\"0 0 34 34\">\n" +
                       "  <path opacity=\"0.5\" fill=\"#785a28\" fill-rule=\"evenodd\" d=\"M13,20h7V13H13v7ZM4,4V26.984l3.955-4L8,8,22.986,8l4-4H4Z\"/>\n" +
                       "  <polygon class=\"active\" fill=\"#c8aa6e\" points=\"29.997 5.955 25 11 25 25 11 25 5.955 29.997 30 30 29.997 5.955\"/>\n" +
                       "</svg>";
            case "SUPPORT":
                return "<svg xmlns=\"http://www.w3.org/2000/svg\" width=\"30\" height=\"30\" viewBox=\"0 0 34 34\">\n" +
                       "    <path class=\"active\" fill=\"#c8aa6e\" fill-rule=\"evenodd\" d=\"M26,13c3.535,0,8-4,8-4H23l-3,3,2,7,5-2-3-4h2ZM22,5L20.827,3H13.062L12,5l5,6Zm-5,9-1-1L13,28l4,3,4-3L18,13ZM11,9H0s4.465,4,8,4h2L7,17l5,2,2-7Z\"/>\n" +
                       "</svg>";
            default:
                return "";
        }
    }
}
