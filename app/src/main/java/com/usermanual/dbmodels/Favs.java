package com.usermanual.dbmodels;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Favs {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public int subtitleId;

    public Favs() {

    }
}
