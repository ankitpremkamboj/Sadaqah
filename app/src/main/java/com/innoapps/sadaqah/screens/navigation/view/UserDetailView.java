package com.innoapps.sadaqah.screens.navigation.view;

import com.innoapps.sadaqah.screens.navigation.model.UserDetailModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;

import java.util.ArrayList;

/**
 * Created by ankit on 6/14/2017.
 */

public interface UserDetailView {

    void getUserDetailsSuccessfull(UserDetailModel.Data data);

    void getUserDetailsUnSuccessful(String message);


}
