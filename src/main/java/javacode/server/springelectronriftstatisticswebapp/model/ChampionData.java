package javacode.server.springelectronriftstatisticswebapp.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ChampionData implements Comparable<ChampionData> {

    public ChampionData () {
    }

    public ChampionData (String ID, String name, Double KDA, String wr, String games) {
        this.ID = ID;
        this.name = name;
        this.KDA = KDA;
        this.wr = wr;
        this.games = games;
    }

    String ID = "";
    String name = "";
    Double KDA = 0.0;
    int kills = 0;
    int deaths = 0;
    int assists = 0;
    String wr = "";
    String wins = "0";
    String losses = "0";
    String games = "0";
    int timesAppeared = 0;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public Double getKDA () {
        return KDA;
    }

    public void setKDA (int kills, int deaths, int assists) {
        this.kills += kills;
        this.deaths += deaths;
        this.assists += assists;

        double KDAAverage = 0;
        if (this.deaths == 0) {
            KDAAverage = (double) this.kills + (double) this.assists;
        }else {
            KDAAverage = ((double) this.kills + (double) this.assists) / (double) this.deaths;
        }
        BigDecimal bd = new BigDecimal(KDAAverage).setScale(2, RoundingMode.HALF_UP);
        this.KDA = bd.doubleValue();
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

    public void hasAppeared() {
        this.timesAppeared++;
    }

    public int getTimesAppeared() {
        return this.timesAppeared;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChampionData that = (ChampionData) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    public void updateWinrate() {
        Double wr = (double) ((Integer.parseInt(this.wins) * 100) / (Integer.parseInt(this.games)));
        this.wr = String.valueOf(wr);
    }

    @Override
    public int compareTo(ChampionData o) {
        return Integer.compare(o.timesAppeared, this.timesAppeared);
    }
}
