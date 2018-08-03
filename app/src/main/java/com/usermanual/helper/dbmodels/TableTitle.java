package com.usermanual.helper.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class TableTitle {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
}
