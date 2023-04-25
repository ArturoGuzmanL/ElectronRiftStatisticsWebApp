package javacode.server.springelectronriftstatisticswebapp.model;

public class SummonerData implements Comparable<SummonerData> {

    public SummonerData () {
    }

    public SummonerData (String gamesPlayedTogether, String name, String imgID, String rank) {
        this.name = name;
        this.imgID = imgID;
        this.rank = rank;
        this.gamesPlayedTogether = gamesPlayedTogether;
    }

    String name;
    String imgID;
    String rank;
    String gamesPlayedTogether;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getImgID () {
        return imgID;
    }

    public void setImgID (String imgID) {
        this.imgID = imgID;
    }

    public String getRank () {
        return rank;
    }

    public void setRank (String rank) {
        this.rank = rank;
    }

    public String getGamesPlayedTogether () {
        return gamesPlayedTogether;
    }

    public void setGamesPlayedTogether (String gamesPlayedTogether) {
        this.gamesPlayedTogether = gamesPlayedTogether;
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

    @Override
    public int compareTo(SummonerData o) {
        return Integer.compare(Integer.parseInt(o.gamesPlayedTogether), Integer.parseInt(this.gamesPlayedTogether));
    }
}
