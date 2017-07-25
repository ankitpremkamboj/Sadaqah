package com.innoapps.sadaqah.screens.history;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.history.model.HistoryModel;
import com.innoapps.sadaqah.screens.history.presenter.HistoryPresenter;
import com.innoapps.sadaqah.screens.history.presenter.HistoryPresenterImpl;
import com.innoapps.sadaqah.screens.history.view.HistoryView;
import com.innoapps.sadaqah.screens.taboption.homefragment.HomeAdapter;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;
import com.innoapps.sadaqah.utils.UserSession;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 6/21/2017.
 */

public class HistoryFragment extends Fragment implements HistoryView {

    @InjectView(R.id.txtTitle)
    TextView txtTitle;
    @InjectView(R.id.curncy)
    TextView curncy;
    @InjectView(R.id.amount)
    TextView amount;
    @InjectView(R.id.txt)
    TextView txt;
    @InjectView(R.id.recycler_view)
    RecyclerView recycler_view;
    ArrayList<HistoryModel.Datum> datumArrayList;

    HistoryAdapter historyAdapter;
    LinearLayoutManager linearLayoutManager;
    HistoryPresenter historyPresenter;
    UserSession userSession;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.history_fragment, container, false);

        ButterKnife.inject(this, view);
        userSession = new UserSession(getActivity());

        historyPresenter = new HistoryPresenterImpl();
        String userID = userSession.getUserID();
        historyPresenter.callGetHistoryList(userID, getActivity(), this);

        // setFont();
        return view;

    }


    @Override
    public void getHistoryListSuccessful(ArrayList<HistoryModel.Datum> datumArrayList) {

        this.datumArrayList = datumArrayList;
        recycler_view.setHasFixedSize(true);
        recycler_view.setItemViewCacheSize(20);
        recycler_view.setDrawingCacheEnabled(true);
        recycler_view.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        recycler_view.setLayoutManager(linearLayoutManager);
        // eventAdapter = new NewAdapter(getActivity(), eventLisdata, eventPresenter, recyclerViewEvent, layoutManager);
        historyAdapter = new HistoryAdapter(getActivity(), datumArrayList);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(historyAdapter);
        // recycler_view.addOnScrollListener(mRecyclerViewOnScrollListener);
    }

    @Override
    public void getHistoryListUnSuccessful(String message) {

    }

    @Override
    public void historyInternetError() {

    }
}
