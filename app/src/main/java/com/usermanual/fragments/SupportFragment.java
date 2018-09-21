package com.usermanual.fragments;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.auth.Auth;
import com.usermanual.helper.FileUtils;
import com.usermanual.helper.NetworkHelper;
import com.usermanual.helper.dbmodels.MessageModel;
import com.usermanual.helper.dbmodels.UploadResponse;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.io.File;

import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportFragment extends Fragment {
    private static final String TAG = "SupportFragment";

    private final int RESULT_LOAD_IMAGE = 100;

    LinearLayout noNet;
    Button gallery;
    Button send;
    EditText question;
    String filePath = null;

    ProgressDialog progressDialog;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_fragment, container, false);
        noNet = (LinearLayout) view.findViewById(R.id.no_net);
        question = (EditText) view.findViewById(R.id.question);
        gallery = (Button) view.findViewById(R.id.gallery);
        send = (Button) view.findViewById(R.id.send);
        progressDialog = new ProgressDialog(getActivity());

        if (!NetworkHelper.isNetworkConnected(getContext())) {
            noNet.setVisibility(View.VISIBLE);
            gallery.setVisibility(View.GONE);
            send.setVisibility(View.GONE);
            question.setVisibility(View.GONE);
        }

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
                if (question.getText().toString().equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.should_not_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
                    if (!progressDialog.isShowing())
                        progressDialog.show();
                    progressDialog.setMessage(getResources().getString(R.string.send_question));
                    MessageModel messageModel = new MessageModel();
                    messageModel.text = question.getText().toString();
                    messageModel.token = Auth.getToken(getContext());
                    messageModel.url = "";
                    Log.e(TAG, "onResponse: " + messageModel.text + "   " + messageModel.url + "   " + messageModel.token);
                    Call<UploadResponse> sendQuestionCall = data.sendQuestion(messageModel);
                    sendQuestionCall.enqueue(new Callback<UploadResponse>() {
                        @Override
                        public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                            if (response.body() != null && response.body().result) {
                                Headers headers = response.headers();
                                Log.e(TAG, "onResponse: " + headers.get("Content-Type"));
                                Toast.makeText(getContext(), "ارسال شد", Toast.LENGTH_SHORT).show();
                            } else if (response.body() != null && !response.body().result)
                                Toast.makeText(getContext(), response.body().error, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<UploadResponse> call, Throwable t) {
                            Toast.makeText(getContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    });

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
            if (filePath == null || filePath.equals(""))
                return;
            final GetData retroData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
            File file = new File(filePath);
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filePath);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            RequestBody description =
                    RequestBody.create(
                            okhttp3.MultipartBody.FORM, "File");
            Call<UploadResponse> responseBodyCall = retroData.upload(description, multipartBody);
            if (!progressDialog.isShowing())
                progressDialog.show();
            progressDialog.setMessage(getResources().getString(R.string.select_file));
            responseBodyCall.enqueue(new Callback<UploadResponse>() {
                @Override
                public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                    if (response.body() != null && response.body().result) {
                        MessageModel messageModel = new MessageModel();
                        messageModel.text = question.getText().toString();
                        messageModel.token = Auth.getToken(getContext());
                        messageModel.url = response.body().fileName;
                        Log.e(TAG, "onResponse: " + messageModel.text + "   " + messageModel.url + "   " + messageModel.token);

                    } else  if (response.body() != null && !response.body().result)
                        Toast.makeText(getContext(), response.body().error, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UploadResponse> call, Throwable t) {
                    Toast.makeText(getContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    progressDialog.dismiss();
                }
            });
        }
    }
}
