package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.helper.dbmodels.TableSubTitle;

import java.util.List;

@Dao
public interface SubtitleDao {

    @Query("SELECT * FROM tablesubtitle WHERE titleId LIKE :titleId")
    List<TableSubTitle> getSubtitles(int titleId);

    @Query("SELECT * FROM tablesubtitle")
    List<TableSubTitle> getAll();

    @Query("SELECT * FROM tablesubtitle WHERE subtitle LIKE :subtitle")
    List<TableSubTitle> search(String subtitle);

    @Query("SELECT * FROM tablesubtitle WHERE subtitle LIKE :subtitle AND titleId = :titleId")
    List<TableSubTitle> search(int titleId, String subtitle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TableSubTitle subTitles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TableSubTitle> subTitles);

    @Query("DELETE FROM TableSubTitle")
    void deleteAll();
}
