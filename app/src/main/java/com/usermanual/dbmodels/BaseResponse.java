package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("error")
    public String error;

    @Override
    public String toString() {
        return "error=" + error;
    }
}
