package com.usermanual.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.auth.Auth;
import com.usermanual.helper.FileUtils;
import com.usermanual.helper.dbmodels.MessageModel;
import com.usermanual.helper.dbmodels.UploadResponse;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportFragment extends Fragment {
    private static final String TAG = "SupportFragment";

    private final int RESULT_LOAD_IMAGE = 100;

    Button gallery;
    Button send;
    EditText question;
    String filePath = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_fragment, container, false);
        question = (EditText) view.findViewById(R.id.question);
        gallery = (Button) view.findViewById(R.id.gallery);
        send = (Button) view.findViewById(R.id.send);

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("*/*");
                intent.setAction(Intent.ACTION_PICK);

                startActivityForResult(intent, RESULT_LOAD_IMAGE);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
                if (filePath != null) {
                    File file = new File(filePath);
                    final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filePath);
                    MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
                    RequestBody description =
                            RequestBody.create(
                                    okhttp3.MultipartBody.FORM, "File");
                    Call<UploadResponse> responseBodyCall = data.upload(description, multipartBody);
                    responseBodyCall.enqueue(new Callback<UploadResponse>() {
                        @Override
                        public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                            if (response.body() != null && response.body().result) {
                                Toast.makeText(getContext(), response.body().error, Toast.LENGTH_SHORT).show();

                                MessageModel messageModel = new MessageModel();
                                messageModel.text = question.getText().toString();
                                messageModel.token = Auth.getToken(getContext());
                                messageModel.url = response.body().fileName;
                                Log.e(TAG, "onResponse: " + messageModel.text + "   " + messageModel.url + "   " + messageModel.token);
                                Call<UploadResponse> sendQuestionCall = data.sendQuestion(messageModel);
                                sendQuestionCall.enqueue(new Callback<UploadResponse>() {
                                    @Override
                                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                                        if (response.body() != null) {
                                            Log.e(TAG, "onResponse: " + response.body().error);
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UploadResponse> call, Throwable t) {

                                    }
                                });

                            }

                        }

                        @Override
                        public void onFailure(Call<UploadResponse> call, Throwable t) {
                            Log.d(TAG, "message = " + t.getMessage());
                            Log.d(TAG, "cause = " + t.getCause());
                        }
                    });

                } else if (filePath == null) {
                    MessageModel messageModel = new MessageModel();
                    messageModel.text = question.getText().toString();
                    messageModel.token = Auth.getToken(getContext());
                    messageModel.url = "";
                    Log.e(TAG, "onResponse: " + messageModel.text + "   " + messageModel.url + "   " + messageModel.token);
                    Call<UploadResponse> sendQuestionCall = data.sendQuestion(messageModel);
                    sendQuestionCall.enqueue(new Callback<UploadResponse>() {
                        @Override
                        public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                            if (response.body() != null) {
                                Log.e(TAG, "onResponse: " + response.body().error);
                            }
                        }

                        @Override
                        public void onFailure(Call<UploadResponse> call, Throwable t) {

                        }
                    });
                }
            }
        });

        return view;
    }

    public static SupportFragment newInstance() {
        SupportFragment supportFragment = new SupportFragment();
        return supportFragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK) {
            filePath = FileUtils.getRealPathFromURI(getContext(), data.getData());
            Log.e(TAG, "onActivityResult: " + filePath);
        }
    }
}
