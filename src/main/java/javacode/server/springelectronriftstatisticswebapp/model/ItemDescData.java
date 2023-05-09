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

    public ItemDescData (String str) {
        String[] arr = str.split(":", 2);
        if (arr.length == 2) {
            this.title = arr[0]+":";
            this.text = arr[1];
        }else {
            this.title = null;
            this.text = str;
        }
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
