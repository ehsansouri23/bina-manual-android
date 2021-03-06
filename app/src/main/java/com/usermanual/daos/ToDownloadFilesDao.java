package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.dbmodels.TableToDownloadFiles;

import java.util.List;

@Dao
public interface ToDownloadFilesDao {

    @Query("SELECT * FROM TableToDownloadFiles")
    List<TableToDownloadFiles> getAll();

    @Query("SELECT * FROM TableToDownloadFiles WHERE fileKey LIKE :fileKey")
    TableToDownloadFiles get(String fileKey);

    @Query("SELECT * FROM TableToDownloadFiles WHERE downloaded = 0")
    List<TableToDownloadFiles> getToDownload();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(TableToDownloadFiles tableToDownloadFiles);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<TableToDownloadFiles> tableToDownloadFiles);

    @Delete
    void delete(TableToDownloadFiles tableToDownloadFiles);

    @Query("DELETE FROM TableToDownloadFiles WHERE fileKey LIKE :fileKey")
    void delete(String fileKey);

    @Query("DELETE FROM TableToDownloadFiles")
    void deleteAll();
}
