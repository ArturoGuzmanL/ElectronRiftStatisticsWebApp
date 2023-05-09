package javacode.server.springelectronriftstatisticswebapp.model;

import java.util.ArrayList;

public class ItemData {
    String name;
    String ID;
    String tooltipName;
    String price;
    String goldType;
    String goldTypeB;
    ArrayList<ItemStatsData> stats;
    ArrayList<ItemDescData> desc;

    public ItemData(String name, String ID, String tooltipName, String price, String goldType, String goldTypeB, ArrayList<ItemStatsData> stats, ArrayList<ItemDescData> desc) {
        this.name = name;
        this.ID = ID;
        this.tooltipName = tooltipName;
        this.price = price;
        this.goldType = goldType;
        this.goldTypeB = goldTypeB;
        this.stats = stats;
        this.desc = desc;
    }

    public ItemData() {
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
        this.tooltipName = name+"T";
    }

    public String getID () {
        return ID;
    }

    public void setID (String ID) {
        this.ID = ID;
    }

    public String getTooltipName () {
        return tooltipName;
    }

    public void setTooltipName (String tooltipName) {
        this.tooltipName = tooltipName;
    }

    public String getPrice () {
        return price;
    }

    public void setPrice (String price) {
        this.price = price;
    }

    public String getGoldType () {
        return goldType;
    }

    public void setGoldType (Integer goldType) {
        if (goldType == 1) {
            this.goldType = "Gold";
            this.goldTypeB = "Gold";
        }else {
            this.goldType = "SilverSerpents";
            this.goldTypeB = "Silver Serpents";
        }
    }

    public String getGoldTypeB () {
        return goldTypeB;
    }

    public void setGoldTypeB (String goldTypeB) {
        this.goldTypeB = goldTypeB;
    }

    public ArrayList<ItemStatsData> getItemStatsData () {
        return stats;
    }

    public void setItemStatsData (ArrayList<ItemStatsData> stats) {
        this.stats = stats;
    }

    public ArrayList<ItemDescData> getItemDescData () {
        return desc;
    }

    public void setItemDescData (ArrayList<ItemDescData> desc) {
        this.desc = desc;
    }

    public void addItemStatData (ItemStatsData stat) {
        if (this.stats == null) {
            this.stats = new ArrayList<ItemStatsData>();
        }
        this.stats.add(stat);
    }

    public void addItemDescData (ItemDescData desc) {
        if (this.desc == null) {
            this.desc = new ArrayList<ItemDescData>();
        }
        this.desc.add(desc);
    }

}
