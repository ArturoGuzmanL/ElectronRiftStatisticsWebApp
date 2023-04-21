package javacode.server.springelectronriftstatisticswebapp.model;

public class ChampionData {

    public ChampionData () {
    }

    public ChampionData (String ID, String name, String KDA, String wr, String games) {
        this.ID = ID;
        this.name = name;
        this.KDA = KDA;
        this.wr = wr;
        this.games = games;
    }

    String ID = "";
    String name = "";
    String KDA = "";
    String wr = "";
    String wins = "0";
    String losses = "0";
    String games = "0";

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getKDA () {
        return KDA;
    }

    public void setKDA (String KDA) {
        this.KDA = KDA;
    }

    public String getWr () {
        return wr;
    }

    public void setWr (String wr) {
        this.wr = wr;
    }

    public String getGames () {
        return games;
    }

    public void setGames (String games) {
        this.games = games;
    }

    public String getID () {
        return ID;
    }

    public void setID (String ID) {
        this.ID = ID;
    }

    public String getWins () {
        return wins;
    }

    public void setWins (String wins) {
        this.wins = wins;
    }

    public void addWin () {
        this.wins = String.valueOf(Integer.parseInt(this.wins) + 1);
        this.games = String.valueOf(Integer.parseInt(this.games) + 1);
    }

    public String getLosses () {
        return losses;
    }

    public void setLosses (String losses) {
        this.losses = losses;
    }

    public void addLoss () {
        this.losses = String.valueOf(Integer.parseInt(this.losses) + 1);
        this.games = String.valueOf(Integer.parseInt(this.games) + 1);
    }

    public void updateWinrate() {
        Double wr = (double) ((Integer.parseInt(this.wins) * 100) / (Integer.parseInt(this.games)));
        this.wr = String.valueOf(wr);
    }
}
