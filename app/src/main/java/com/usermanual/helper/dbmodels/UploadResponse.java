package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

public class UploadResponse {

    @SerializedName("Result")
    public boolean result;

    @SerializedName("Error")
    public String error;

    @SerializedName("FileName")
    public String fileName;
}
