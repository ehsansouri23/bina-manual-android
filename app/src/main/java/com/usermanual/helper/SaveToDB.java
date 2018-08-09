package com.usermanual.helper;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.usermanual.R;
import com.usermanual.activities.MainActivity;
import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;

import java.util.List;

import static com.usermanual.helper.NetworkHelper.getJSON;
import static com.usermanual.helper.PrefHelper.MEDIAS_URL;
import static com.usermanual.helper.PrefHelper.SUBTITLES_URL;
import static com.usermanual.helper.PrefHelper.TITLES_URL;

public class SaveToDB extends AsyncTask<Void, Void, Boolean> {

    ProgressDialog progressDialog;
    MainActivity activity;
    List<TableTitle> titles;
    List<TableSubTitle> subTitles;
    List<TableMedia> medias;
    Gson gson;

    public SaveToDB(MainActivity activity, ProgressDialog progressDialog) {
        this.activity = activity;
        this.progressDialog = progressDialog;
    }

    @Override
    protected void onPreExecute() {
        progressDialog.setCancelable(false);
        progressDialog.setMessage(activity.getResources().getString(R.string.reciving_data));
        progressDialog.show();
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        String titlesJSON = getJSON(TITLES_URL, 5000);
        gson = new Gson();
        titles = gson.fromJson(titlesJSON, new TypeToken<List<TableTitle>>(){}.getType());
        if (titles == null)
            return false;
        DataBaseHelper.saveTitles(activity.getApplicationContext(), titles);

        for (int i = 0; i < titles.size(); i++) {
            String subtitleUrl = SUBTITLES_URL + "/" + titles.get(i).titleId;
            String subtitleJSON = getJSON(subtitleUrl, 5000);
            subTitles = gson.fromJson(subtitleJSON, new TypeToken<List<TableSubTitle>>(){}.getType());
            if (subTitles == null)
                return false;
            DataBaseHelper.saveSubtitles(activity.getApplicationContext(), subTitles);

            for (int j = 0; j < subTitles.size(); j++) {
                String mediaUrl = MEDIAS_URL +  "/" + subTitles.get(j).subtitleId;
                String mediaJSON = getJSON(mediaUrl, 5000);
                medias = gson.fromJson(mediaJSON, new TypeToken<List<TableSubTitle>>(){}.getType());
                if (medias == null)
                    return false;
                DataBaseHelper.saveMedias(activity.getApplicationContext(), medias);
            }
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
            activity.onDataLoaded(aBoolean);
    }
}
