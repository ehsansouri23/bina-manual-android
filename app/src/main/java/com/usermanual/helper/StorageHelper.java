package com.usermanual.helper;

import android.content.Context;

import java.io.File;

import static com.usermanual.helper.Consts.*;

public class StorageHelper {

    public static File getFile(Context context, String fileName) {
        File baseFile = context.getDir("hello", Context.MODE_PRIVATE);
        File dir = new File(baseFile, fileName);
        if (!dir.exists())
            dir.mkdirs();
        return dir;
    }

    public static void deleteFile(Context context, String fileName) {
        File baseFile = getFile(context, fileName);
        File file = new File(baseFile, fileName);
        if (file.exists())
            file.delete();
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
