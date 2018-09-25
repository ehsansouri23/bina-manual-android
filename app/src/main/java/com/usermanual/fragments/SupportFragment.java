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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.usermanual.R;
import com.usermanual.auth.Auth;
import com.usermanual.dbmodels.MessageModel;
import com.usermanual.dbmodels.UploadResponse;
import com.usermanual.helper.FileUtils;
import com.usermanual.helper.NetworkHelper;
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

    LinearLayout noNet;
    Button gallery;
    Button send;
    ImageView deleteFile;
    EditText question;
    String filePath = null;
    String fileUrl = null;

    ProgressDialog progressDialog;

    boolean uploadSuccess = true;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_fragment, container, false);
        noNet = (LinearLayout) view.findViewById(R.id.no_net);
        question = (EditText) view.findViewById(R.id.question);
        gallery = (Button) view.findViewById(R.id.gallery);
        deleteFile = (ImageView) view.findViewById(R.id.delete_file);
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

        deleteFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), getResources().getString(R.string.file_deleted), Toast.LENGTH_SHORT).show();
                deleteFile.setVisibility(View.GONE);
                fileUrl = null;
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: " + uploadSuccess);
                if (question.getText().toString().equals("")) {
                    Toast.makeText(getContext(), getResources().getString(R.string.should_not_empty), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!uploadSuccess) {
                    Toast.makeText(getContext(), getResources().getString(R.string.no_uploaded), Toast.LENGTH_SHORT).show();
                    return;
                }
                final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
                if (!progressDialog.isShowing())
                    progressDialog.show();
                progressDialog.setMessage(getResources().getString(R.string.send_question));
                MessageModel messageModel = new MessageModel();
                messageModel.text = question.getText().toString();
                messageModel.token = Auth.getToken(getContext());
                if (fileUrl == null || fileUrl.equals(""))
                    messageModel.url = "";
                else messageModel.url = fileUrl;
                Log.d(TAG,  "sending message: text: " + messageModel.text + " fileKey: " + messageModel.url + " token: " + messageModel.token);
                Call<UploadResponse> sendQuestionCall = data.sendQuestion(messageModel);
                sendQuestionCall.enqueue(new Callback<UploadResponse>() {
                    @Override
                    public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                        if (response.body() != null && response.body().result) {
                            Toast.makeText(getContext(), getResources().getString(R.string.sent), Toast.LENGTH_SHORT).show();
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
            Log.d(TAG, "onActivityResult: filePath: " + filePath);
            if (filePath == null || filePath.equals(""))
                return;
            final GetData retroData = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
            File file = new File(filePath);
            if (!file.exists())
                Log.e(TAG, "onActivityResult: file not exists");
            final RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), filePath);
            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
            RequestBody description = RequestBody.create(okhttp3.MultipartBody.FORM, Auth.getToken(getContext()));
            final Call<UploadResponse> uploadCall = retroData.upload(description, multipartBody);
            if (!progressDialog.isShowing())
                progressDialog.show();
            progressDialog.setMessage(getResources().getString(R.string.select_file));
            uploadCall.enqueue(new Callback<UploadResponse>() {
                @Override
                public void onResponse(Call<UploadResponse> call, Response<UploadResponse> response) {
                    if (response.body() != null && response.body().result) {
                        Toast.makeText(getContext(), getResources().getString(R.string.uploaded), Toast.LENGTH_SHORT).show();
                        uploadSuccess = true;
                        deleteFile.setVisibility(View.VISIBLE);
                        fileUrl = response.body().fileName;
                        Log.d(TAG, "onResponse: uploaded fileKey: " + fileUrl);
                    } else if (response.body() != null && !response.body().result) {
                        Toast.makeText(getContext(), response.body().error, Toast.LENGTH_SHORT).show();
                        uploadSuccess = false;
                    }
                    progressDialog.dismiss();
                }

                @Override
                public void onFailure(Call<UploadResponse> call, Throwable t) {
                    Toast.makeText(getContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "onFailure: uploading failed. message: " + t.getMessage());
                    progressDialog.dismiss();
                    uploadSuccess = false;
                }
            });
        }
    }
}
