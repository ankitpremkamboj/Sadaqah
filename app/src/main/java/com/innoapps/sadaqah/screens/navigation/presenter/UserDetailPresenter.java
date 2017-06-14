package com.innoapps.sadaqah.screens.navigation.presenter;

import android.app.Activity;

import com.innoapps.sadaqah.screens.navigation.view.UserDetailView;

/**
 * Created by ankit on 6/14/2017.
 */

public interface UserDetailPresenter {

    void gettingUserDetails(Activity activity, UserDetailView userDetailView,String userId);
}
