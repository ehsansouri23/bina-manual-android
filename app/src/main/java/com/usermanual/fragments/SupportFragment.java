package com.usermanual.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.nononsenseapps.filepicker.FilePickerActivity;
import com.nononsenseapps.filepicker.Utils;
import com.usermanual.R;
import com.usermanual.auth.Auth;
import com.usermanual.helper.dbmodels.MessageModel;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "SupportFragment";

    private final int RESULT_LOAD_IMAGE = 100;

    Button gallery;
    Button send;
    EditText question;

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
                Intent i = new Intent(getContext(), FilePickerActivity.class);
                // This works if you defined the intent filter
                // Intent i = new Intent(Intent.ACTION_GET_CONTENT);

                // Set these depending on your use case. These are the defaults.
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_MULTIPLE, false);
                i.putExtra(FilePickerActivity.EXTRA_ALLOW_CREATE_DIR, false);
                i.putExtra(FilePickerActivity.EXTRA_MODE, FilePickerActivity.MODE_FILE);

                // Configure initial directory by specifying a String.
                // You could specify a String like "/storage/emulated/0/", but that can
                // dangerous. Always use Android's API calls to get paths to the SD-card or
                // internal memory.
                i.putExtra(FilePickerActivity.EXTRA_START_PATH, Environment.getExternalStorageDirectory().getPath());

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.send) { //todo check it
            MessageModel messageModel = new MessageModel();
            String questionText = question.getText().toString();
            messageModel.text = questionText;
            messageModel.token = Auth.getToken(getContext());
            final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
            Call<MessageModel> sendQuestionCall = data.sendQuestion(messageModel);
            sendQuestionCall.enqueue(new Callback<MessageModel>() {
                @Override
                public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {

                }

                @Override
                public void onFailure(Call<MessageModel> call, Throwable t) {

                }
            });
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK) {
            // Use the provided utility method to parse the result
            List<Uri> files = Utils.getSelectedFilesFromResult(data);
            for (Uri uri : files) {
                File file = Utils.getFileForUri(uri);
                Log.e(TAG, "onActivityResult: " + file.getAbsolutePath());
                // Do something with the result...
            }
        }
    }
}
