package com.usermanual.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.adapter.TicketMessagesAdapter;
import com.usermanual.auth.Auth;
import com.usermanual.dbmodels.BaseResponse;
import com.usermanual.dbmodels.MessageModel;
import com.usermanual.dbmodels.TicketMessageModel;
import com.usermanual.dbmodels.UploadResponse;
import com.usermanual.fragments.MediaFragment;
import com.usermanual.helper.Consts;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketActivity extends AppCompatActivity {
    private static final String TAG = "TicketActivity";
    private static final int REQUEST_IMAGE_CAPTURE = 110;
    private static final int REQUEST_VIDEO_CAPTURE = 111;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.message)
    EditText messageEditText;
    @BindView(R.id.send_message)
    ImageView sendMessage;
    @BindView(R.id.send_file)
    ImageView sendFile;
    @BindView(R.id.chats_list)
    RecyclerView chatsList;

    @BindString(R.string.toolbar_title)
    String toolbarTitle;

    @BindString(R.string.select)
    String selectItems;

    @BindString(R.string.send_pic)
    String sendPicString;

    @BindString(R.string.send_video)
    String sendVideoString;

    GetData data;
    int ticketId;

    ProgressDialog progressDialog;
    TicketMessagesAdapter ticketMessagesAdapter;

    @OnClick(R.id.send_file)
    void openSendFileDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(selectItems);
        String[] s = {sendPicString, sendVideoString};
        dialogBuilder.setItems(s, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0)
                    dispatchTakePictureIntent();
                if (i == 1)
                    dispatchTakeVideoIntent();
            }
        });
        dialogBuilder.show();
    }

    @OnClick(R.id.send_message)
    void sendMessage() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        String message = messageEditText.getText().toString();
        MessageModel messageModel = new MessageModel(ticketId, message, Consts.VIDEO);
        data.sendMessage(Auth.getToken(getApplicationContext()), messageModel).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response.body().error == null || response.body().error.equals("")) {
                    Log.d(TAG, "sending message: successful");
                    fetchMessages();
                } else {
                    Log.e(TAG, "sending message: error");
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(TicketActivity.this);
        progressDialog.setCancelable(false);

        data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        ticketMessagesAdapter = new TicketMessagesAdapter(getApplicationContext(), new MediaFragment.OnClick() {
            @Override
            public void onClick(int type, String key) {
                if (type == Consts.IMAGE) {
                    Intent imageActivityIntent = new Intent(TicketActivity.this, ImageViewActivity.class);
                    imageActivityIntent.putExtra(Consts.FILE_URL, key);
                    startActivity(imageActivityIntent);
                } else if (type == Consts.VIDEO) {

                }
            }
        });
        chatsList.setAdapter(ticketMessagesAdapter);
        chatsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            toolbar.setTitle(extras.getString(Consts.TITLE));
            ticketId = extras.getInt(Consts.TICKET_ID);
        } else toolbar.setTitle(toolbarTitle);

        fetchMessages();
    }

    private void fetchMessages() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        data.getAllMessageus(Consts.BASE_URL + Consts.GET_MESSAGES_URL + ticketId, Auth.getToken(getApplicationContext())).enqueue(new Callback<List<TicketMessageModel>>() {
            @Override
            public void onResponse(Call<List<TicketMessageModel>> call, Response<List<TicketMessageModel>> response) {
                if (response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.d(TAG, "getting messages: " + response.body().get(i));
                    }
                    ticketMessagesAdapter.setMessageModels(response.body());
                    ticketMessagesAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onFailure(Call<List<TicketMessageModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Log.e(TAG, "onActivityResult: " + data.getExtras().get("data"));
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
        }
    }
}
