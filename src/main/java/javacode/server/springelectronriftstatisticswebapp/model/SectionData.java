package javacode.server.springelectronriftstatisticswebapp.model;

import java.util.ArrayList;

public class SectionData {
    String name;
    ArrayList<ItemData> items;

    public SectionData(String name, ArrayList<ItemData> items) {
        this.name = name;
        this.items = items;
    }

    public SectionData() {
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<ItemData> getItems() {
        return items;
    }
    public void setItems(ArrayList<ItemData> items) {
        this.items = items;
    }
}
