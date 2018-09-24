package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {

    @SerializedName("Token")
    public String token;

    @SerializedName("Name")
    public String name= "";

    @SerializedName("Pic")
    public String picFileKey = "";

}
