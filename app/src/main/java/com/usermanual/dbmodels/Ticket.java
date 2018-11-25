package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class Ticket {

    @SerializedName("id")
    public int id;

    @SerializedName("ticket_name")
    public String ticketName;

    @SerializedName("done")
    public int isDone;

//    @SerializedName("created_at")
//    public String creationDate;
}
