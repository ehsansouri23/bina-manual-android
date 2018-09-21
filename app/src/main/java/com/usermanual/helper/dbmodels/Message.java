package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("Id")
    public int messageId;

    @SerializedName("UserId")
    public int userId;

    @SerializedName("Text")
    public String text;

    @SerializedName("FileAddress")
    public String fileAddress;

    @SerializedName("Created")
    public String created;
}
