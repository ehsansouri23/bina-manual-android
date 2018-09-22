package com.usermanual.helper;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.usermanual.daos.FavsDao;
import com.usermanual.daos.FileDao;
import com.usermanual.daos.MediaDao;
import com.usermanual.daos.SubMediaDao;
import com.usermanual.daos.SubtitleDao;
import com.usermanual.daos.TitleDao;
import com.usermanual.daos.ToDownloadFilesDao;
import com.usermanual.dbmodels.Favs;
import com.usermanual.dbmodels.FileModel;
import com.usermanual.dbmodels.TableMedia;
import com.usermanual.dbmodels.TableSubMedia;
import com.usermanual.dbmodels.TableSubTitle;
import com.usermanual.dbmodels.TableTitle;
import com.usermanual.dbmodels.TableToDownloadFiles;

@Database(entities = {
        TableSubTitle.class,
        TableMedia.class,
        TableTitle.class,
        TableSubMedia.class,
        TableToDownloadFiles.class,
        FileModel.class,
        Favs.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract TitleDao titleDao();

    public abstract SubtitleDao subtitleDao();

    public abstract MediaDao mediaDao();

    public abstract SubMediaDao subMediaDao();

    public abstract ToDownloadFilesDao toDownloadFilesDao();

    public abstract FileDao fileModelDao();

    public abstract FavsDao favsDao();

    public static AppDatabase getInstance(Context context) {
        if (instance != null)
            return instance;
        instance = Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, "db").allowMainThreadQueries().build();
        return instance;
    }
}
