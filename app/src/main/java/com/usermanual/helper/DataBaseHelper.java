package com.usermanual.helper;

import android.content.Context;

import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;

import java.util.List;

public class DataBaseHelper {

    public static void saveTitles(Context context, List<TableTitle> titles) {
        AppDatabase.getInstance(context).titleDao().insertAll(titles);
    }

    public static void saveSubtitles(Context context, List<TableSubTitle> subTitles) {
        AppDatabase.getInstance(context).subtitleDao().insertAll(subTitles);
    }

    public static void saveMedias(Context context, List<TableMedia> medias) {
        AppDatabase.getInstance(context).mediaDao().insertAll(medias);
    }

    public static List<TableTitle> getTitlesList(Context context) {
        return AppDatabase.getInstance(context).titleDao().getAll();
    }

    public static List<TableTitle> searchTitle(Context context, String titleRegex) {
        return AppDatabase.getInstance(context).titleDao().search("%" + titleRegex + "%");
    }

    public static List<TableSubTitle> getSubtitlesList(Context context, int titleId) {
        return AppDatabase.getInstance(context).subtitleDao().getSubtitles(titleId);
    }

    public static List<TableSubTitle> searchSubtitle(Context context, String subtitleRegex) {
        return AppDatabase.getInstance(context).subtitleDao().search("%" + subtitleRegex + "%");
    }

    public static List<TableSubTitle> searchSubtitle(Context context, int titleId, String subtitleRegex) {
        return AppDatabase.getInstance(context).subtitleDao().search(titleId, "%" + subtitleRegex + "%");
    }

    public static List<TableMedia> getMediaList(Context context, int subtitleId) {
        return AppDatabase.getInstance(context).mediaDao().getMedias(subtitleId);
    }

    public static void deleteAllTitles(Context context) {
        AppDatabase.getInstance(context).titleDao().deleteAll();
    }

    public static void deleteAllSubtitles(Context context) {
        AppDatabase.getInstance(context).subtitleDao().deleteAll();
    }

    public static void deleteAllMedias(Context context) {
        AppDatabase.getInstance(context).mediaDao().deleteAll();
    }
}
