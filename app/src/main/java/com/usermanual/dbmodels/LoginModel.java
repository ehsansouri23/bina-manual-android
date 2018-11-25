package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("username")
    public String userName;

    @SerializedName("password")
    public String password;

    @SerializedName("imei")
    public String imei;
}
