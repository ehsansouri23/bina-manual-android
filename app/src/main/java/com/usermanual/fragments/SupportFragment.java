package com.usermanual.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.usermanual.R;
import com.usermanual.adapter.TicketsAdapter;
import com.usermanual.dbmodels.Ticket;
import com.usermanual.helper.NetworkHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SupportFragment extends Fragment {
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
            //todo post to server
        });
        dialogBuilder.show();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.support_fragment, container, false);
        ButterKnife.bind(this, view);
//        progressDialog = new ProgressDialog(getActivity());
//        progressDialog.setCancelable(false);

        ticketsList.showShimmerAdapter();
        ticketsList.setLayoutManager(new LinearLayoutManager(getContext()));

        if (!NetworkHelper.isNetworkConnected(getContext())) {
            noNet.setVisibility(View.VISIBLE);
        }

        return view;
    }

    public static SupportFragment newInstance() {
        SupportFragment supportFragment = new SupportFragment();
        return supportFragment;
    }
}
