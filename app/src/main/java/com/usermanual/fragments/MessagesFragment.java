package com.usermanual.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.usermanual.R;
import com.usermanual.adapter.MessagedAdapter;
import com.usermanual.auth.Auth;
import com.usermanual.dbmodels.MessageResponse;
import com.usermanual.network.GetData;
import com.usermanual.network.RetrofitClientInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MessagesFragment extends Fragment {

    RecyclerView list;
    MessagedAdapter messagedAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.messages_fragment, container, false);
        list = (RecyclerView) v.findViewById(R.id.list);
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<MessageResponse> getMessagesCall = data.getMessages(Auth.getTokenModel(getContext()));
        getMessagesCall.enqueue(new Callback<MessageResponse>() {
            @Override
            public void onResponse(Call<MessageResponse> call, Response<MessageResponse> response) {

            }

            @Override
            public void onFailure(Call<MessageResponse> call, Throwable t) {

            }
        });
        return v;

    }
}
