package com.usermanual.helper;

import android.content.Context;
import android.util.Log;

import com.usermanual.helper.dbmodels.TableMedia;
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
                AppDatabase.getInstance(context).subtitleDao().insert(tableSubTitles.get(j));
            }

            for (int j = 0; j < subTitles.size(); j++) {
                List<Media> mediaList = subTitles.get(j).getMedias();
                List<TableMedia> tableMediaList = new ArrayList<>();

                for (int k = 0; k < mediaList.size(); k++) {
                    TableMedia tableMedia = new TableMedia();
                    tableMedia.mediaText = mediaList.get(k).getText();
                    tableMedia.mediaUrl = mediaList.get(k).getUrl();
                    tableMedia.title = titleList.get(i).getTitle();
                    tableMedia.subtitle = subTitles.get(j).getSubTitle();
                    tableMediaList.add(tableMedia);
                }
                for (int k = 0; k < tableMediaList.size(); k++) {
                    Log.e("tag", "saveToDB: " + AppDatabase.getInstance(context).mediaDao().insert(tableMediaList.get(k)));
                }
            }

        }
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
        List<String> subtitleStrings = new ArrayList<>();
        for (int i = 0; i < subTitles.size(); i++) {
            subtitleStrings.add(subTitles.get(i).subtitle);
        }
        return subtitleStrings;
    }

    public static List<Media> getMediaList(Context context, String subTitle) {
        List<TableMedia> medias = AppDatabase.getInstance(context).mediaDao().getMedias(subTitle);
        List<Media> mediaList = new ArrayList<>();
        for (int i = 0; i < medias.size(); i++) {
            Media media = new Media(medias.get(i).mediaText, medias.get(i).mediaUrl, 0);
            mediaList.add(media);
        }
        return mediaList;
    }

}
