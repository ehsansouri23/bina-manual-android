package com.usermanual.helper.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class TableSubTitle {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("Titleid")
    public int titleId;

    @SerializedName("Id")
    public int subtitleId;

    public String title;

    @SerializedName("Title")
    public String subtitle;
}
