package com.usermanual.helper;

import android.os.Environment;

import java.io.File;

public class StorageHelper {

    public static String getDir() {
        return Environment.getExternalStorageDirectory().toString() + "/.ttld/tyui/.loikjh/";
    }

    public static boolean fileExists(String fileName) {
        File file = new File(getDir() + fileName);
        return file.exists();
    }
}
