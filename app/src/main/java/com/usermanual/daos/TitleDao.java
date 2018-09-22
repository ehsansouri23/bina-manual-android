package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.dbmodels.TableTitle;

import java.util.List;

@Dao
public interface TitleDao {

    @Query("SELECT * FROM TableTitle")
    List<TableTitle> getAll();

    @Query("SELECT * FROM TableTitle WHERE titleId LIKE :titleId")
    TableTitle getTitle(int titleId);

    @Query("SELECT * FROM TableTitle WHERE title LIKE :title")
    List<TableTitle> search(String title);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TableTitle tableTitle);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TableTitle> tableTitles);

    @Query("DELETE FROM TableTitle")
    void deleteAll();
}
