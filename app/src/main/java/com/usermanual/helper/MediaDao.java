package com.usermanual.helper;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.helper.dbmodels.TableMedia;

import java.util.List;

@Dao
public interface MediaDao {

    @Query("SELECT * FROM tablemedia WHERE subtitleId = :subtitleId")
    List<TableMedia> getMedias(int subtitleId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TableMedia media);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TableMedia> medias);

    @Query("DELETE FROM TableMedia")
    void deleteAll();
}
