package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("Id")
    public int newsId;

    @SerializedName("title")
    public String title = "";

    @SerializedName("url")
    public String fileKey;

    @SerializedName("txt")
    public String text;
}
