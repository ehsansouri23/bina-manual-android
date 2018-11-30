package com.usermanual.network;

import com.usermanual.dbmodels.BaseResponse;
import com.usermanual.dbmodels.LoginModel;
import com.usermanual.dbmodels.LoginResponse;
import com.usermanual.dbmodels.MessageModel;
import com.usermanual.dbmodels.NewsModel;
import com.usermanual.dbmodels.TableMedia;
import com.usermanual.dbmodels.TableSubMedia;
import com.usermanual.dbmodels.TableSubTitle;
import com.usermanual.dbmodels.TableTitle;
import com.usermanual.dbmodels.Ticket;
import com.usermanual.dbmodels.TicketMessageModel;
import com.usermanual.dbmodels.UploadResponse;

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

import static com.usermanual.helper.Consts.ADD_TICKET;
import static com.usermanual.helper.Consts.GET_TICKETS;
import static com.usermanual.helper.Consts.LOGIN_URL;
import static com.usermanual.helper.Consts.MEDIAS_URL;
import static com.usermanual.helper.Consts.NEWS_URL;
import static com.usermanual.helper.Consts.SEND_MESSAGE_URL;
import static com.usermanual.helper.Consts.SUBTITLES_URL;
import static com.usermanual.helper.Consts.TITLES_URL;
import static com.usermanual.helper.Consts.TOKEN_HEADER;
import static com.usermanual.helper.Consts.UPDOAD_FILE;

public interface GetData {

    @POST(LOGIN_URL)
    Call<LoginResponse> login(@Body LoginModel loginModel);

    @GET(TITLES_URL)
    Call<List<TableTitle>> getTitles(@Header(TOKEN_HEADER) String token);

    @GET(SUBTITLES_URL)
    Call<List<TableSubTitle>> getSubtitles(@Header(TOKEN_HEADER) String token);

    @GET(MEDIAS_URL)
    Call<List<TableMedia>> getMedias(@Header(TOKEN_HEADER) String token);

    @GET
    Call<List<TableSubMedia>> getSubMedia(@Header(TOKEN_HEADER) String token, @Url String url);

    @GET
    Call<List<TableSubMedia>> getSubMedias(@Url String url, @Header(TOKEN_HEADER) String token);

    @GET(NEWS_URL)
    Call<List<NewsModel>> getNewsList(@Header(TOKEN_HEADER) String token);

    @GET(GET_TICKETS)
    Call<List<Ticket>> getTickets(@Header(TOKEN_HEADER) String token);

    @POST(ADD_TICKET)
    Call<BaseResponse> addTicket(@Header(TOKEN_HEADER) String token, @Body Ticket ticket);

    @POST(SEND_MESSAGE_URL)
    Call<BaseResponse> sendMessage(@Header(TOKEN_HEADER) String token, @Body MessageModel messageModel);

    @GET
    Call<List<TicketMessageModel>> getAllMessageus(@Url String url, @Header(TOKEN_HEADER) String token);

    @POST
    Call<ResponseBody> downloadFile(@Url String fileUrl);

    @Multipart
    @POST(UPDOAD_FILE)//todo
    Call<UploadResponse> upload(
            @Part()RequestBody description,
            @Part MultipartBody.Part file
            );
}
