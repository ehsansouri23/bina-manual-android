package com.usermanual.helper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubTitle;

@Database(entities = {TableSubTitle.class, TableMedia.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract SubtitleDao subtitleDao();

    public abstract MediaDao mediaDao();

    public static AppDatabase getInstance(Context context) {
        if (instance != null)
            return instance;
        instance = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "db").allowMainThreadQueries().build();
        return instance;
    }
}
