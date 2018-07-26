package com.usermanual.helper;

import android.content.Context;

import com.usermanual.model.Media;
import com.usermanual.model.Title;

import java.util.ArrayList;
import java.util.List;

import static com.usermanual.helper.PrefHelper.getInt;
import static com.usermanual.helper.PrefHelper.getString;
import static com.usermanual.helper.PrefHelper.saveInt;
import static com.usermanual.helper.PrefHelper.saveString;

public class DataBaseHelper {

    public static void saveToDB(Context context, List<Title> titleList) {
        for (int i = 0; i < titleList.size(); i++) {
            saveString(context, i + 1 + "", titleList.get(i).getTitle());
            for (int j = 0; j < titleList.get(i).getSubTitles().size(); j++) {
                saveString(context, generateSubTitlesKey(i + 1, j + 1), titleList.get(i).getSubTitles().get(j).getSubTitle());
                for (int k = 0; k < titleList.get(i).getSubTitles().get(j).getMedias().size(); k++) {
                    saveString(context, generateMediaTextKey(i + 1, j + 1, k + 1), titleList.get(i).getSubTitles().get(j).getMedias().get(k).getText());
                    saveString(context, generateMediaUrlKey(i + 1, j + 1, k + 1), titleList.get(i).getSubTitles().get(j).getMedias().get(k).getUrl());
                    saveInt(context, generateMediaTypeKey(i + 1, j + 1, k + 1), titleList.get(i).getSubTitles().get(j).getMedias().get(k).getType());
                }
            }
        }
    }

    private static String generateSubTitlesKey(int title, int subTitle) {
        return title + "_" + subTitle;
    }

    private static String generateMediaTextKey(int title, int subTitle, int media) {
        return title + "_" + subTitle + "_" + media + "_T";
    }

    private static String generateMediaUrlKey(int title, int subTitle, int media) {
        return title + "_" + subTitle + "_" + media + "_U";
    }

    private static String generateMediaTypeKey(int title, int subTitle, int media) {
        return title + "_" + subTitle + "_" + media + "_Y";
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

    public static List<Media> getMediaList(Context context, int title, int subTitle) {
        List<Media> mediaList = new ArrayList<>();
        int i = 1;
        String key = generateMediaTextKey(title, subTitle, i);
        String media = getString(context, key, null);
        while (media != null) {
            Media media1 = new Media(getString(context, generateMediaTextKey(title, subTitle, i), null)
                    , getString(context, generateMediaUrlKey(title, subTitle, i), null),
                    getInt(context, generateMediaTypeKey(title, subTitle, i), -1));
            mediaList.add(media1);
            i++;
            key = generateMediaTextKey(title, subTitle, i);
            media = getString(context, key, null);
        }
        return mediaList;
    }

}
