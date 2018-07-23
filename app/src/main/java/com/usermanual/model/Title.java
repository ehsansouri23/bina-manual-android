package com.usermanual.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Title {
    String title;

    @SerializedName("sub")
    List<SubTitle> subTitles;

    public String getTitle() {
        return title;
    }

    public List<SubTitle> getSubTitles() {
        return subTitles;
    }
}
