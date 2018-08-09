package com.usermanual.helper.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

@Entity
public class TableTitle {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @SerializedName("Id")
    public int titleId;

    @SerializedName("Title")
    public String title;
}
