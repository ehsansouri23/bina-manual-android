package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

public class LoginModel {

    @SerializedName("Username")
    public String userName;

    @SerializedName("Password")
    public String password;
}
