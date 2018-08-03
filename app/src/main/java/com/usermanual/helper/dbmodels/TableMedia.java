package com.usermanual.helper.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TableMedia {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String subtitle;
    public String mediaText;
    public String mediaUrl;
}
