package com.usermanual.helper;

import android.content.Context;
import android.util.Log;

import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.model.Media;
import com.usermanual.model.SubTitle;
import com.usermanual.model.Title;

import java.util.ArrayList;
import java.util.List;

import static com.usermanual.helper.PrefHelper.getInt;
import static com.usermanual.helper.PrefHelper.getString;
import static com.usermanual.helper.PrefHelper.saveString;

public class DataBaseHelper {

    public static void saveToDB(final Context context, List<Title> titleList) {
        for (int i = 0; i < titleList.size(); i++) {
            saveString(context, i + 1 + "", titleList.get(i).getTitle());
            final List<SubTitle> subTitles = titleList.get(i).getSubTitles();
            List<TableSubTitle> tableSubTitles = new ArrayList<>();
            for (int j = 0; j < subTitles.size(); j++) {
                TableSubTitle tableSubTitle = new TableSubTitle();
                tableSubTitle.title = titleList.get(i).getTitle();
                tableSubTitle.subtitle = subTitles.get(j).getSubTitle();
                tableSubTitles.add(tableSubTitle);
            }
            for (int j = 0; j < tableSubTitles.size(); j++) {
                Log.e("tag", "saveToDB: key: " + AppDatabase.getInstance(context).subtitleDao().insert(tableSubTitles.get(j)));
            }
//            for (int j = 0; j < subTitles.size(); j++) {
//                subTitlesArray[j] = subTitles.get(j);
//            }
//            Log.e("tag", "saveToDB: " + subTitlesArray.length);
//            AppDatabase.getInstance(context).subtitleDao().insert(subTitlesArray);
        }

//            for (int j = 0; j < titleList.get(i).getSubTitles().size(); j++) {
//                saveString(context, generateSubTitlesKey(i + 1, j + 1), titleList.get(i).getSubTitles().get(j).getSubTitle());
//                for (int k = 0; k < titleList.get(i).getSubTitles().get(j).getMedias().size(); k++) {
//                    saveString(context, generateMediaTextKey(i + 1, j + 1, k + 1), titleList.get(i).getSubTitles().get(j).getMedias().get(k).getText());
//                    saveString(context, generateMediaUrlKey(i + 1, j + 1, k + 1), titleList.get(i).getSubTitles().get(j).getMedias().get(k).getUrl());
//                    saveInt(context, generateMediaTypeKey(i + 1, j + 1, k + 1), titleList.get(i).getSubTitles().get(j).getMedias().get(k).getType());
//                }
    }
//        }


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

    public static List<String> getSubtitleList(Context context, String title) {
        List<TableSubTitle> subTitles = AppDatabase.getInstance(context).subtitleDao().getSubtitles(title);
        for (int i = 0; i < subTitles.size(); i++) {
            Log.e("tag", "getSubtitleList: " + subTitles.get(i).title + "  " + subTitles.get(i).subtitle);
        }
        List<String> subtitleStrings = new ArrayList<>();
        for (int i = 0; i < subTitles.size(); i++) {
            subtitleStrings.add(subTitles.get(i).subtitle);
        }
        return subtitleStrings;
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
