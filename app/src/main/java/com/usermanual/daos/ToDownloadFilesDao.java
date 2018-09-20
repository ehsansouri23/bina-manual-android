package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.helper.dbmodels.TableToDownloadFiles;

import java.util.List;

@Dao
public interface ToDownloadFilesDao {

    @Query("SELECT * FROM TableToDownloadFiles")
    List<TableToDownloadFiles> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TableToDownloadFiles tableToDownloadFiles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TableToDownloadFiles> tableToDownloadFiles);

    @Delete
    void delete(TableToDownloadFiles tableToDownloadFiles);

    @Query("DELETE FROM TableToDownloadFiles")
    void deleteAll();
}
