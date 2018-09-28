package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.usermanual.dbmodels.DownloadId;

@Dao
public interface DownloadIdDao {

    @Query("SELECT * FROM DownloadId WHERE fileKey LIKE :fileKey")
    DownloadId getDownloadId(String fileKey);

    @Insert
    long insert(DownloadId downloadId);

    @Query("DELETE FROM DownloadId")
    void deleteAll();

    @Query("DELETE FROM DownloadId WHERE fileKey LIKE :fileKey")
    void delete(String fileKey);
}
