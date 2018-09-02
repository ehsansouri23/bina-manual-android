package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("ID")
    public int newsId;

    @SerializedName("Title")
    public String title;

    @SerializedName("Url")
    public String picUrl;
}
