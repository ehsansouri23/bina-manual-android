package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MessageResponse extends BaseResponse {

    @SerializedName("Messages")
    public List<Message> messages;
}
