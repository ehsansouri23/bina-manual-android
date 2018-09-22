package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("Username")
    public String userName;

    @SerializedName("Password")
    public String password;

    @SerializedName("IMEI")
    public String imei;
}
