package com.usermanual.model;

public class Media {
    String text;
    String url;
    int type;

    public Media(String text, String url, int type) {
        this.text = text;
        this.url = url;
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }

    public int getType() {
        return type;
    }
}
