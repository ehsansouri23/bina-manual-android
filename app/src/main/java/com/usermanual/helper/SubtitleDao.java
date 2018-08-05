package com.usermanual.helper;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.helper.dbmodels.TableSubTitle;

import java.util.List;

@Dao
public interface SubtitleDao {

    @Query("SELECT * FROM tablesubtitle WHERE title = :title")
    List<TableSubTitle> getSubtitles(String title);

    @Query("SELECT * FROM tablesubtitle WHERE subtitle LIKE :subtitle")
    List<TableSubTitle> search(String subtitle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TableSubTitle subTitles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TableSubTitle> subTitles);

}
