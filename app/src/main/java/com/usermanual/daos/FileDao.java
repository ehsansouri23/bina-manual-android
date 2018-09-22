package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.usermanual.dbmodels.FileModel;

import java.util.List;

@Dao
public interface FileDao {

    @Query("SELECT * FROM FILEMODEL WHERE fileKey LIKE :fileKey")
    FileModel getFileModel(String fileKey);

    @Insert
    long insert(FileModel fileModel);

    @Insert
    long[] insertAll(List<FileModel> fileModelList);

    @Query("DELETE FROM FileModel")
    void deleteAll();
}
