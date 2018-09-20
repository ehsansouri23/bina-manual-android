package com.usermanual.helper;

import android.content.Context;

import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubMedia;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;
import com.usermanual.helper.dbmodels.TableToDownloadFiles;

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

    public static void saveSubmedias(Context context, List<TableSubMedia> submedias) {
        AppDatabase.getInstance(context).subMediaDao().insertAll(submedias);
    }

    public static void saveToDownloadFiles(Context context, List<TableToDownloadFiles> toDownloadFiles) {
        AppDatabase.getInstance(context).toDownloadFilesDao().insertAll(toDownloadFiles);
    }

    public static void savetoDownloadFile(Context context, TableToDownloadFiles toDownloadFiles) {
        AppDatabase.getInstance(context).toDownloadFilesDao().insert(toDownloadFiles);
    }

    public static List<TableTitle> getTitlesList(Context context) {
        return AppDatabase.getInstance(context).titleDao().getAll();
    }

    public static TableTitle getTitle(Context context, int titleId) {
        return AppDatabase.getInstance(context).titleDao().getTitle(titleId);
    }

    public static List<TableTitle> searchTitles(Context context, String titleRegex) {
        return AppDatabase.getInstance(context).titleDao().search("%" + titleRegex + "%");
    }

    public static List<TableSubTitle> getSubtitlesList(Context context, int titleId) {
        return AppDatabase.getInstance(context).subtitleDao().getSubtitles(titleId);
    }

    public static TableSubTitle getSubtitle(Context context, int subtitleId) {
        return AppDatabase.getInstance(context).subtitleDao().getSubtitle(subtitleId);
    }

    public static List<TableSubTitle> searchSubtitles(Context context, String subtitleRegex) {
        return AppDatabase.getInstance(context).subtitleDao().search("%" + subtitleRegex + "%");
    }

    public static List<TableSubTitle> searchSubtitles(Context context, int titleId, String subtitleRegex) {
        return AppDatabase.getInstance(context).subtitleDao().search(titleId, "%" + subtitleRegex + "%");
    }

    public static List<TableMedia> getMediaList(Context context, int subtitleId) {
        return AppDatabase.getInstance(context).mediaDao().getMedias(subtitleId);
    }

    public static List<TableSubMedia> getSubmedias(Context context, int mediaId) {
        return AppDatabase.getInstance(context).subMediaDao().getSubMedias(mediaId);
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

    public static void deleteAllSubmedias(Context context) {
        AppDatabase.getInstance(context).subMediaDao().deleteAll();
    }

    public static void deleteAllToDownloadFiles(Context context) {
        AppDatabase.getInstance(context).toDownloadFilesDao().deleteAll();
    }

    public static void deleteToDownloadFile(Context context, TableToDownloadFiles toDownloadFiles) {
        AppDatabase.getInstance(context).toDownloadFilesDao().delete(toDownloadFiles);
    }
}
