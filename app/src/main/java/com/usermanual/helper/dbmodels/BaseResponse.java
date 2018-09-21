package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("Result")
    public boolean result;

    @SerializedName("Error")
    public String error;
}
