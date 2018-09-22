package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.dbmodels.TableSubMedia;

import java.util.List;

@Dao
public interface SubMediaDao {

    @Query("SELECT * FROM tablesubmedia WHERE parentSubMediaId LIKE :mediaId")
    List<TableSubMedia> getSubMedias(int mediaId);

    @Query("SELECT * FROM tablesubmedia")
    List<TableSubMedia> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TableSubMedia> submedias);

    @Query("DELETE FROM TableSubMedia")
    void deleteAll();
}
