package com.example.backendtest;

import com.google.gson.annotations.SerializedName;

public class Github {

    @SerializedName("title")
    private String title;
    @SerializedName("link")
    private String link;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

}