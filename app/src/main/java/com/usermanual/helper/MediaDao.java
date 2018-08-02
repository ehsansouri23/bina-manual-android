package com.usermanual.helper;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.model.Media;

import java.util.List;

@Dao
public interface MediaDao {

    @Query("SELECT * FROM tablemedia WHERE subtitle = :subtitle")
    List<TableMedia> getMedias(String subtitle);

    @Insert
    long insert(TableMedia media);
}
