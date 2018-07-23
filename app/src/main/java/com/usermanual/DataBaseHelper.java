package com.usermanual;

import android.content.Context;

import com.usermanual.model.Title;

import java.util.List;

import static com.usermanual.helper.PrefHelper.*;

public class DataBaseHelper {

    public static void saveToDB(Context context, List<Title> titleList) {
        for (int i = 0; i < titleList.size(); i++) {
            saveString(context, i + 1 + "", titleList.get(i).getTitle());
            for (int j = 0; j < titleList.get(i).getSubTitles().size(); j++) {
                saveString(context, generateSubTitlesKey(i + 1, j + 1), titleList.get(i).getSubTitles().get(j).getSubTitle());
            }
        }
    }

    private static String generateSubTitlesKey(int title, int subTitle) {
        return title + "_" + subTitle;
    }

}
