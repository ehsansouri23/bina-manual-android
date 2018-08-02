package com.usermanual.helper;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.usermanual.helper.dbmodels.TableSubTitle;

import java.util.List;

@Dao
public interface SubtitleDao {

    @Query("SELECT * FROM tablesubtitle WHERE title = :title")
    List<TableSubTitle> getSubtitles(String title);

    @Insert
    long insert(TableSubTitle subTitles);

}
