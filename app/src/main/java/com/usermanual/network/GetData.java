package com.usermanual.network;

import com.usermanual.helper.dbmodels.LoginModel;
import com.usermanual.helper.dbmodels.LoginResponse;
import com.usermanual.helper.dbmodels.MessageModel;
import com.usermanual.helper.dbmodels.NewsModel;
import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubMedia;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface GetData {

    @POST("login")
    Call<LoginResponse> login(@Body LoginModel loginModel);

    @GET("titles")
    Call<List<TableTitle>> getTitles(@Header("token") String token);

    @GET("subtitles")
    Call<List<TableSubTitle>> getSubtitles(@Header("token") String token);

    @GET("medias")
    Call<List<TableMedia>> getMedias(@Header("token") String token);

    @GET("submedias")
    Call<List<TableSubMedia>> getAllSubMedias(@Header("token") String token);

    @GET("news")
    Call<List<NewsModel>> getNewsList(@Header("token") String token);

    @POST("ticket/sendMessage")
    Call<MessageModel> sendQuestion(@Body MessageModel messageModel);

    @POST
    Call<ResponseBody> downloadFile(@Url String fileUrl);
}
