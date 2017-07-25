package com.innoapps.sadaqah.screens.history.view;

import com.innoapps.sadaqah.screens.history.model.HistoryModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;

import java.util.ArrayList;

/**
 * Created by ankit on 7/3/2017.
 */

public interface HistoryView {

    void getHistoryListSuccessful(ArrayList<HistoryModel.Datum> datumArrayList);

    void getHistoryListUnSuccessful(String message);


    void historyInternetError();

}
