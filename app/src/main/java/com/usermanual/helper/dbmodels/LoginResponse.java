package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

public class LoginResponse extends BaseResponse {

    @SerializedName("Token")
    public String token;

}
