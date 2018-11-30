package com.usermanual.helper;

import android.content.Context;

import java.io.File;

import static com.usermanual.helper.Consts.*;

public class StorageHelper {

    public static String getUrl(String fileKey) {
        return BASE_SIMPLE_URL + fileKey;
    }

    public static File getFile(Context context, String fileKey) {
        File baseFile = context.getDir("app", Context.MODE_PRIVATE);
        if (fileKey.lastIndexOf("/") >= 0)
            fileKey = fileKey.substring(fileKey.lastIndexOf("/"));
        return new File(baseFile, fileKey + "/" + fileKey);
    }

    public static int getFileType(String fileType) {
        if (fileType == null || fileType.equals("")) {
            return IMAGE;
        }
        if (fileType.contains("image"))
            return IMAGE;
        else if (fileType.contains("video"))
            return VIDEO;
        return IMAGE;
    }

    public static void createFilesDataBase(Context context) {
        File baseFile = context.getDir("app", Context.MODE_PRIVATE);
        if (!baseFile.exists())
            baseFile.mkdirs();
    }

    public static File getBaseFile(Context context) {
        return context.getDir("app", Context.MODE_PRIVATE);
    }

    public enum FileType {
        TITLES, SUBTITLES, MEDIAS
    }
}
