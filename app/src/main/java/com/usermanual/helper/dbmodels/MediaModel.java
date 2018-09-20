package com.usermanual.helper.dbmodels;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MediaModel {

    @SerializedName("SubtitleId")
    public int subtitleId;

    @SerializedName("Title")
    public String title;

    @SerializedName("Medias")
    public List<Media> medias;

    class Media {
        @SerializedName("Text")
        public String text;

        @SerializedName("Url")
        public String url;

        @SerializedName("Type")
        public int type;
    }
}
