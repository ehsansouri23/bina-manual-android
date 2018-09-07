package com.usermanual.network;

import com.usermanual.helper.dbmodels.FullNewsModel;
import com.usermanual.helper.dbmodels.NewsModel;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface GetData {

    @GET("titles")
    Call<List<TableTitle>> getTitles();

    @GET()
    Call<List<TableSubTitle>> getSubtitles(@Url String s);

    @GET("news")
    Call<List<NewsModel>> getNewsList();

    @GET()
    Call<FullNewsModel> getHtmlNews(@Url String url);

    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
