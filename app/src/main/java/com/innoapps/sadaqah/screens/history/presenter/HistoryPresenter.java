package com.innoapps.sadaqah.screens.history.presenter;

import android.app.Activity;

import com.innoapps.sadaqah.screens.history.view.HistoryView;
import com.innoapps.sadaqah.screens.taboption.homefragment.view.HomeView;

/**
 * Created by ankit on 7/3/2017.
 */

public interface HistoryPresenter {
    void callGetHistoryList(String userID, Activity activity, HistoryView historyView);

}
