package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class TicketMessageModel {

    @SerializedName("pid")
    public int ticketId;

    @SerializedName("msgfrom")
    public int from;//1 for client. 0 for admin

    @SerializedName("msg")
    public String message;

    @SerializedName("url")
    public String url;

    @SerializedName("type")
    public int type;

    @Override
    public String toString() {
        return "msgfrom=" + from + "    msg=" + message + "    url=" + url + "    type=" + type;
    }
}
