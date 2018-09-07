package com.usermanual.helper;

import android.content.Context;

import java.io.File;

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
}
