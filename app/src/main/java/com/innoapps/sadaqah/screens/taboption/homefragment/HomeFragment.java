package com.innoapps.sadaqah.screens.taboption.homefragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.navigation.NavigationActivity;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.ReportModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.presenter.HomePresenter;
import com.innoapps.sadaqah.screens.taboption.homefragment.presenter.HomePresenterImpl;
import com.innoapps.sadaqah.screens.taboption.homefragment.view.HomeView;
import com.innoapps.sadaqah.utils.SnackNotify;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 6/7/2017.
 */

public class HomeFragment extends Fragment implements HomeView {


    /* @InjectView(R.id.lay)
     LinearLayout lay;*/
    HomePresenter homePresenter;
    HomeAdapter homeAdapter;
    @InjectView(R.id.coordinateLayout)
    RelativeLayout coordinateLayout;
    @InjectView(R.id.adView)
    AdView adView;
    @InjectView(R.id.recycler_view)
    RecyclerView recycler_view;

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


    }

    private void callApi() {
        homePresenter.callGetHomeList("a", getActivity(), this);
    }

    @Override
    public void getHomeListSuccessfull(ArrayList<HomeModel.Datum> datumArrayList) {

        this.datumArrayList = datumArrayList;
        HomeAdapter homeAd = new HomeAdapter(getActivity(), datumArrayList, homePresenter);
        recycler_view.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recycler_view.setAdapter(homeAd);
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