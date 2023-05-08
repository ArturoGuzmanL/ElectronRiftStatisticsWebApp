package javacode.server.springelectronriftstatisticswebapp.model;

public class ItemDescData {
    String title;
    String text;

    public ItemDescData(String title, String text) {
        this.title = title;
        this.text = text;
    }

    public ItemDescData() {
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
}
