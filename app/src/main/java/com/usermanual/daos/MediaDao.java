package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.dbmodels.TableMedia;

import java.util.List;

@Dao
public interface MediaDao {

    @Query("SELECT * FROM tablemedia WHERE parentSubtitleId LIKE :subtitleId")
    List<TableMedia> getMedias(int subtitleId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TableMedia media);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TableMedia> medias);

    @Query("DELETE FROM TableMedia")
    void deleteAll();
}
