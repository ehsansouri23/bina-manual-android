package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

public class NewsModel {

    @SerializedName("ID")
    public int newsId;

    @SerializedName("Title")
    public String title;

    @SerializedName("FileName")
    public String picUrl;

    @SerializedName("Text")
    public String fullHtml;
}
