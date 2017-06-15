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
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.presenter.HomePresenter;
import com.innoapps.sadaqah.screens.taboption.homefragment.presenter.HomePresenterImpl;
import com.innoapps.sadaqah.screens.taboption.homefragment.view.HomeView;
import com.innoapps.sadaqah.utils.SnackNotify;

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
    @InjectView(R.id.coordinateLayout)
    RelativeLayout coordinateLayout;
    @InjectView(R.id.adView)
    AdView adView;

    ArrayList<HomeModel.Datum> datumArrayList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        ButterKnife.inject(this, view);
        AdRequest adRequest = new AdRequest.Builder()
                .build();
        adView.loadAd(adRequest);
        loadAds();
        // setFont();
        return view;

    }


    private void loadAds() {

        try {

            AdRequest adRequest = new AdRequest.Builder()
                    //                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                    .addTestDevice("BC66CD84AE30139003DB301E2AB6525F")
                    .build();

            adView.loadAd(adRequest);
            // Load ads into Interstitial Ads
        } catch (Exception e) {
            e.printStackTrace();
        }

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
        homeAdapter = new HomeAdapter(getActivity(), datumArrayList, homePresenter);
        gridView.setAdapter(homeAdapter);

    }

    @Override
    public void getHomeListUnSuccessful(String message) {

        SnackNotify.showMessage(message, coordinateLayout);

    }

    @Override
    public void homeInternetError() {
        SnackNotify.showMessage(getString(R.string.internet_check), coordinateLayout);
    }

    @Override
    public void onPause() {
        if (adView != null) {
            adView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adView != null) {
            adView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (adView != null) {
            adView.destroy();
        }
        super.onDestroy();
    }
}