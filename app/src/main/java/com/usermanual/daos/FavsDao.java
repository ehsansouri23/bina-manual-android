package com.usermanual.daos;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.usermanual.dbmodels.Favs;

import java.util.List;

@Dao
public interface FavsDao {

    @Query("SELECT * FROM Favs")
    List<Favs> getAll();

    @Query("SELECT * FROM Favs WHERE subtitleId LIKE :subtitleId")
    Favs get(int subtitleId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Favs favs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insertAll(List<Favs> favsList);

    @Query("DELETE FROM Favs WHERE subtitleId LIKE :subtitleId")
    void delete(int subtitleId);
}
