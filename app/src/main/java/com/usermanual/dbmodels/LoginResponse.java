package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("id")
    public String token;

    @SerializedName("name")
    public String name= "";

    @SerializedName("url")
    public String picFileUrl = "";

    @Override
    public String toString() {
        return "token=" + token +
                "\nname=" + name +
                "\nfileKey=" + picFileUrl;
    }
}
