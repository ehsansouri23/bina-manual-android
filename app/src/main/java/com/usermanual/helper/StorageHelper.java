package com.usermanual.helper;

import android.content.Context;

import java.io.File;

import static com.usermanual.helper.Consts.*;

public class StorageHelper {

    public static String getUrl(String fileKey) {
        return BASE_URL + FILE_URL + fileKey;
    }

    public static File getFile(Context context, String fileKey) {
        File baseFile = context.getDir("app", Context.MODE_PRIVATE);
        return new File(baseFile, fileKey);
    }

    public static int getFileType(String fileType) {
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

    public static class FileSpec {
        String fileKey;
        String url;
        File file;

        public FileSpec(Context context, String fileKey, FileType fileType) {
            this.url = BASE_URL + FILE_URL + fileKey;
            this.fileKey = fileKey;
            File baseFile = context.getDir("app", Context.MODE_PRIVATE);
            String fileName = "";
            if (fileType == FileType.TITLES)
                fileName = "titles/" + fileKey;
            else if (fileType == FileType.SUBTITLES)
                fileName = "subtitles/" + fileKey;
            else if (fileType == FileType.MEDIAS)
                fileName = "medias/" + fileKey;
            this.file = new File(baseFile, fileName);
        }

        public File getFile() {
            return file;
        }

        public String getUrl() {
            return url;
        }

        public String getFileKey() {
            return fileKey;
        }

        public String getFileType() {
            return "";
        }
    }

    public enum FileType {
        TITLES, SUBTITLES, MEDIAS
    }
}
