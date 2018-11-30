package com.usermanual.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.usermanual.ItemClickListener;
import com.usermanual.R;
import com.usermanual.activities.TicketActivity;
import com.usermanual.adapter.TicketsAdapter;
import com.usermanual.auth.Auth;
import com.usermanual.dbmodels.BaseResponse;
import com.usermanual.dbmodels.Ticket;
import com.usermanual.helper.Consts;
import com.usermanual.helper.NetworkHelper;
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

public class SupportFragment extends Fragment implements ItemClickListener {
    private static final String TAG = "SupportFragment";

    @BindView(R.id.no_net)
    LinearLayout noNet;
    @BindView(R.id.tickets_list)
    ShimmerRecyclerView ticketsList;
    @BindView(R.id.add_fab)
    FloatingActionButton addFab;

    @BindString(R.string.enter_ticket_title)
    String ticketTitleString;
    @BindString(R.string.submit)
    String submit;

    List<Ticket> tickets;
    ProgressDialog progressDialog;
    TicketsAdapter ticketsAdapter;

    boolean uploadSuccess = true;

    @OnClick(R.id.add_fab)
    void openAddTicketDialog() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        dialogBuilder.setTitle(ticketTitleString);
        final EditText input = new EditText(getActivity());
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        dialogBuilder.setView(input);
        dialogBuilder.setPositiveButton(submit, (dialogInterface, i) -> {
            Ticket ticket = new Ticket();
            ticket.isDone = 0;
            ticket.ticketName = input.getText().toString();
            final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
            data.addTicket(Auth.getToken(getContext()), ticket).enqueue(new Callback<BaseResponse>() {
                @Override
                public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                    if (response.body().error == null || response.body().error.equals("")) {
                        tickets.add(ticket);
                        ticketsAdapter.notifyDataSetChanged();
                    } else {
                        Log.d(TAG, "onResponse: error occurred." + response.body().error);
                    }
                }

                @Override
                public void onFailure(Call<BaseResponse> call, Throwable t) {
                    Toast.makeText(getContext(), getResources().getString(R.string.retry_restart_again), Toast.LENGTH_SHORT).show();
                }
            });
        });
        dialogBuilder.show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_fragment, container, false);
        ButterKnife.bind(this, view);
        ticketsList.showShimmerAdapter();
        ticketsList.setLayoutManager(new LinearLayoutManager(getContext()));
        final GetData data = RetrofitClientInstance.getRetrofitInstance().create(GetData.class);
        Call<List<Ticket>> call = data.getTickets(Auth.getToken(getContext()));
        call.enqueue(new Callback<List<Ticket>>() {
            @Override
            public void onResponse(Call<List<Ticket>> call, Response<List<Ticket>> response) {
                if (response.body() != null) {
                    tickets = response.body();
                    for (int i = 0; i < response.body().size(); i++) {
                        Log.d(TAG, "getting tickets: id=" + response.body().get(i).id + " name=" + response.body().get(i).ticketName + " isDone=" + response.body().get(i).isDone);
                    }
                    ticketsAdapter = new TicketsAdapter(getContext(), response.body(), SupportFragment.this);
                    ticketsList.hideShimmerAdapter();
                    ticketsList.setAdapter(ticketsAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Ticket>> call, Throwable t) {

            }
        });
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(false);


        if (!NetworkHelper.isNetworkConnected(getContext())) {
            noNet.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public static SupportFragment newInstance() {
        SupportFragment supportFragment = new SupportFragment();
        return supportFragment;
    }

    @Override
    public void onItemClick(View v, int id, String url) {
        Log.d(TAG, "ItemClickListener: ticketId=" + id);
        Intent messageActivityIntent = new Intent(getActivity(), TicketActivity.class);
        messageActivityIntent.putExtra(Consts.TICKET_ID, id);
        startActivity(messageActivityIntent);
    }
}
