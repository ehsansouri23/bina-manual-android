package com.usermanual.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

    String imageFilePath;

    @OnClick(R.id.send_file)
    void openSendFileDialog() {
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        dialogBuilder.setTitle(selectItems);
//        String[] s = {sendPicString, sendVideoString};
//        dialogBuilder.setItems(s, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                if (i == 0) {
//                    try {
//                        dispatchTakePictureIntent();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                }
//                if (i == 1)
//                    dispatchTakeVideoIntent();
//            }
//        });
//        dialogBuilder.show();

        try {
            dispatchTakePictureIntent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.send_message)
    void sendMessage() {
        if (!progressDialog.isShowing())
            progressDialog.show();
        String message = messageEditText.getText().toString();
        MessageModel messageModel = new MessageModel(ticketId, message, Consts.TEXT);
        Log.d(TAG, "message model: " + messageModel);
        data.sendMessage(Auth.getToken(getApplicationContext()), messageModel).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                Log.e(TAG, "sending text response: " + response.body());
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
                    Intent videoActivityIntent = new Intent(TicketActivity.this, VideoViewActivity.class);
                    videoActivityIntent.putExtra(Consts.FILE_URL, key);
                    startActivity(videoActivityIntent);
                }
            }
        });
        chatsList.setAdapter(ticketMessagesAdapter);
        chatsList.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            toolbar.setTitle(extras.getString(Consts.TITLE));
            ticketId = extras.getInt(Consts.TICKET_ID);
        } else {
            toolbar.setTitle(toolbarTitle);
            ticketId = -1;
        }

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

    private void dispatchTakePictureIntent() throws IOException {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            //Create a file to store the image
            File photoFile = null;
            photoFile = createImageFile();
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.usermanual.provider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private void dispatchTakeVideoIntent() {
        Intent takeVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (takeVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takeVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp =
                new SimpleDateFormat("yyyyMMdd_HHmmss",
                        Locale.getDefault()).format(new Date());
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir =
                getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        imageFilePath = image.getAbsolutePath();
        return image;
    }

    private static byte[] loadFile(File file) throws IOException {
        InputStream is = new FileInputStream(file);

        long length = file.length();
        if (length > Integer.MAX_VALUE) {
            return null;
        }
        byte[] bytes = new byte[(int) length];

        int offset = 0;
        int numRead = 0;
        while (offset < bytes.length
                && (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {
            offset += numRead;
        }

        if (offset < bytes.length) {
            throw new IOException("Could not completely read file " + file.getName());
        }

        is.close();
        return bytes;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
//            if (!progressDialog.isShowing())
//                progressDialog.show();
//            new Base64ImageFile(new Completed() {
//                @Override
//                public void completed(String en) {
//
//                }
//            }).execute();
            File imageFile = new File(imageFilePath);
            String en = null;
            try {
                en = Base64.encodeToString(loadFile(imageFile), Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MessageModel messageModel = new MessageModel(ticketId, en, Consts.IMAGE);
            Log.e(TAG, "sending image: " + messageModel.type);
            GetData data2 = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
            data2.sendMessage(Auth.getToken(getApplicationContext()), messageModel).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    Log.e(TAG, "image response: " + response.body());
                    if (response.body().error == null || response.body().error.equals("")) {
                        Log.d(TAG, "sending message: successful");
                        fetchMessages();
                    } else {
                        Log.e(TAG, "sending message: error");
                        progressDialog.dismiss();
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            });
        }
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
        }
    }

    class Base64ImageFile extends AsyncTask<Void, Void, Void> {
        Completed completed;

        Base64ImageFile(Completed completed) {
            this.completed = completed;
        }

        @Override
        protected Void doInBackground(Void... voids) {
//            if (!progressDialog.isShowing())
//                progressDialog.show();
            File imageFile = new File(imageFilePath);
            String en = null;
            try {
                en = Base64.encodeToString(loadFile(imageFile), Base64.DEFAULT);
            } catch (IOException e) {
                e.printStackTrace();
            }
            completed.completed(en);
            return null;
        }
    }

    interface Completed {
        void completed(String en);
    }
}
