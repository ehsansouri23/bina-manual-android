package com.usermanual.helper;

import android.content.Context;

import com.usermanual.dbmodels.TableMedia;
import com.usermanual.dbmodels.TableSubMedia;
import com.usermanual.dbmodels.TableSubTitle;
import com.usermanual.dbmodels.TableTitle;
import com.usermanual.dbmodels.TableToDownloadFiles;

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

    public static void saveToDownloadFile(Context context, TableToDownloadFiles toDownloadFiles) {
        AppDatabase.getInstance(context).toDownloadFilesDao().insert(toDownloadFiles);
    }

    public static void saveToDownloadFile(Context context, String fileKey, int type) {
        TableToDownloadFiles tableToDownloadFiles = new TableToDownloadFiles(fileKey, type, 0);
        saveToDownloadFile(context, tableToDownloadFiles);
    }

    public static boolean isFileDownloaded(Context context, String fileKey) {
        TableToDownloadFiles toDownloadFiles = AppDatabase.getInstance(context).toDownloadFilesDao().get(fileKey);
        if (toDownloadFiles == null)
            return false;
        else if (toDownloadFiles.downloaded == 0)
            return false;
        else return true;
    }

    public static void fileDownloaded(Context context, String fileKey) {
        TableToDownloadFiles toDownloadFiles = AppDatabase.getInstance(context).toDownloadFilesDao().get(fileKey);
        if (toDownloadFiles != null) {
            toDownloadFiles.downloaded = 1;
            AppDatabase.getInstance(context).toDownloadFilesDao().insert(toDownloadFiles);
        }
    }

    public static void saveFav(Context context, int subtitleId) {
        TableSubTitle subTitle = AppDatabase.getInstance(context).subtitleDao().getSubtitle(subtitleId);
        subTitle.saved = 1;
        AppDatabase.getInstance(context).subtitleDao().insert(subTitle);
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

    public static List<TableToDownloadFiles> getToDownloadFiles(Context context) {
        return AppDatabase.getInstance(context).toDownloadFilesDao().getToDownload();
    }

    public static List<TableSubTitle> getAllFavs(Context context) {
        return AppDatabase.getInstance(context).subtitleDao().getSaved();
    }

    public static boolean saved(Context context, int subtitleId) {
        TableSubTitle subTitle = AppDatabase.getInstance(context).subtitleDao().getSubtitle(subtitleId);
        if (subTitle != null) {
            if (subTitle.saved == 0)
                return false;
            else return true;
        }
        return false;
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

    public static void deleteToDownloadFile(Context context, String fileKey) {
        AppDatabase.getInstance(context).toDownloadFilesDao().delete(fileKey);
    }

    public static void deleteFav(Context context, int subtitleId) {
        TableSubTitle subTitle = AppDatabase.getInstance(context).subtitleDao().getSubtitle(subtitleId);
        subTitle.saved = 0;
        AppDatabase.getInstance(context).subtitleDao().insert(subTitle);
    }
}
