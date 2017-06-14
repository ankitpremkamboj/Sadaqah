package com.innoapps.sadaqah.screens.taboption.homefragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.presenter.HomePresenter;
import com.innoapps.sadaqah.screens.taboption.homefragment.presenter.HomePresenterImpl;
import com.innoapps.sadaqah.screens.taboption.homefragment.view.HomeView;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ankit on 6/7/2017.
 */

public class HomeFragment extends Fragment implements HomeView {

    @InjectView(R.id.gridView)
    GridView gridView;
    /* @InjectView(R.id.lay)
     LinearLayout lay;*/
    HomePresenter homePresenter;
    HomeAdapter homeAdapter;

    ArrayList<HomeModel.Datum> datumArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        ButterKnife.inject(this, view);

        // setFont();
        return view;

    }
/*
    @OnClick(R.id.lay)
    public void dialog() {
        showDialog();
    }*/

    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.home_diaolog);


        dialog.show();

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


        homePresenter = new HomePresenterImpl();


        callApi();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             ///   showDialog();
            }
        });


    }

    private void callApi() {
        homePresenter.callGetHomeList("a", getActivity(), this);
    }

    @Override
    public void getHomeListSuccessfull(ArrayList<HomeModel.Datum> datumArrayList) {

        this.datumArrayList = datumArrayList;
        homeAdapter = new HomeAdapter(getActivity(), datumArrayList);
        gridView.setAdapter(homeAdapter);

    }

    @Override
    public void getHomeListUnSuccessful(String message) {

    }
}