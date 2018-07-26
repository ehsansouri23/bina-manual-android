package com.usermanual.helper;

import android.content.Context;

import com.usermanual.model.Title;

import java.util.ArrayList;
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

    public static List<String> getTitlesList(Context context) {
        List<String> titles = new ArrayList<>();
        int i = 1;
        String title = getString(context, i + "", null);
        while (title != null) {
            titles.add(title);
            i++;
            title = getString(context, i + "", null);
        }
        return titles;
    }

    public static List<String> getSubtitleList(Context context, int title) {
        List<String> subtitles = new ArrayList<>();
        int i = 1;
        String key = generateSubTitlesKey(title, i);
        String subtitle = getString(context, key, null);
        while (subtitle != null) {
            subtitles.add(subtitle);
            i++;
            key = generateSubTitlesKey(title, i);
            subtitle = getString(context, key, null);
        }
        return subtitles;
    }

}
