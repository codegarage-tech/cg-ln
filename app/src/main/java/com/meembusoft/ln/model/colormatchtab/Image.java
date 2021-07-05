package com.meembusoft.ln.model.colormatchtab;

public class Image {

    private String url;

    public Image(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "{" +
                "url='" + url + '\'' +
                '}';
    }
}