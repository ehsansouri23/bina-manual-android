package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class Ticket {

    @SerializedName("id")
    public int id;

    @SerializedName("ticketName")
    public String ticketName;

    public boolean isOpen;
}
