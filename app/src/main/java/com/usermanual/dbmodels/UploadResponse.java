package com.usermanual.dbmodels;

import com.google.gson.annotations.SerializedName;

public class UploadResponse extends BaseResponse {

    @SerializedName("FileName")
    public String fileName;
}
