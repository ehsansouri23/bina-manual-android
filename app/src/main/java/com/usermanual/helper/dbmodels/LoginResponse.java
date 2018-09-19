package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("Result")
    public boolean result;

    @SerializedName("Token")
    public String token;

    @SerializedName("Error")
    public String error;

}
