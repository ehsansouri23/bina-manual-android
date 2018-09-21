package com.usermanual.network;

import com.usermanual.helper.dbmodels.LoginModel;
import com.usermanual.helper.dbmodels.LoginResponse;
import com.usermanual.helper.dbmodels.MessageModel;
import com.usermanual.helper.dbmodels.NewsModel;
import com.usermanual.helper.dbmodels.TableMedia;
import com.usermanual.helper.dbmodels.TableSubMedia;
import com.usermanual.helper.dbmodels.TableSubTitle;
import com.usermanual.helper.dbmodels.TableTitle;
import com.usermanual.helper.dbmodels.UploadResponse;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Url;

import static com.usermanual.helper.Consts.LOGIN_URL;
import static com.usermanual.helper.Consts.MEDIAS_URL;
import static com.usermanual.helper.Consts.NEWS_URL;
import static com.usermanual.helper.Consts.SEND_MESSAGE_URL;
import static com.usermanual.helper.Consts.SUBMEDIAS_URL;
import static com.usermanual.helper.Consts.SUBTITLES_URL;
import static com.usermanual.helper.Consts.TITLES_URL;
import static com.usermanual.helper.Consts.UPDOAD_FILE;

public interface GetData {

    @POST(LOGIN_URL)
    Call<LoginResponse> login(@Body LoginModel loginModel);

    @GET(TITLES_URL)
    Call<List<TableTitle>> getTitles(@Header("token") String token);

    @GET(SUBTITLES_URL)
    Call<List<TableSubTitle>> getSubtitles(@Header("token") String token);

    @GET(MEDIAS_URL)
    Call<List<TableMedia>> getMedias(@Header("token") String token);

    @GET
    Call<List<TableSubMedia>> getSubMedia(@Header("token") String token, @Url String url);

    @GET(SUBMEDIAS_URL)
    Call<List<TableSubMedia>> getSubMedias(@Header("token") String token);

    @GET(NEWS_URL)
    Call<List<NewsModel>> getNewsList(@Header("token") String token);

    @POST(SEND_MESSAGE_URL)
    Call<UploadResponse> sendQuestion(@Body MessageModel messageModel);

    @POST
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    @Multipart
    @POST(UPDOAD_FILE)
    Call<UploadResponse> upload(
            @Part("description")RequestBody description,
            @Part MultipartBody.Part file
            );
}
