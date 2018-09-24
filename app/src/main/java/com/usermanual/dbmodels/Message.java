package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class Message {
    @SerializedName("Id")
    public int messageId;

    @SerializedName("UserId")
    public int userId;

    @SerializedName("Text")
    public String text;

    @SerializedName("FileAddress")
    public String fileKey;

    @SerializedName("Created")
    public String created;

    @SerializedName("Type")
    public String fileType;
}
