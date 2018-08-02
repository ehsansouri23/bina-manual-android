package com.usermanual.helper;

import android.os.Environment;

import java.io.File;
import java.io.IOException;

public class StorageHelper {

    public static String getDir() {
        return Environment.getExternalStorageDirectory().toString() + "/.ttld/tyui/.loikjh/";
    }

    public static File getFile(String fileName) {
        File file = new File(getDir() + fileName);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public static void deleteFile(String fileName) {
        File file = new File(getDir() + fileName);
        if (file.exists())
            file.delete();
    }
}
