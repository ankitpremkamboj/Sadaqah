package com.innoapps.sadaqah.screens.taboption.homefragment.view;

import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;

import java.util.ArrayList;

/**
 * Created by ankit on 6/9/2017.
 */

public interface HomeView {

    void getHomeListSuccessfull(ArrayList<HomeModel.Datum> datumArrayList);

    void getHomeListUnSuccessful(String message);


}
