package com.usermanual.helper;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.usermanual.helper.dbmodels.TableTitle;

import java.util.List;

@Dao
public interface TitleDao {

    @Query("SELECT * FROM TableTitle")
    List<TableTitle> getAll();

    @Query("SELECT * FROM TableTitle WHERE title LIKE :title")
    List<TableTitle> search(String title);

    @Insert
    long insert(TableTitle tableTitle);
}
