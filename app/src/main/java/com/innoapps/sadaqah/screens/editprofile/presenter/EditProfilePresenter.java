package com.innoapps.sadaqah.screens.editprofile.presenter;

import android.app.Activity;

import com.innoapps.sadaqah.screens.editprofile.view.EditProfileView;
import com.innoapps.sadaqah.screens.signup.view.SignUpView;

/**
 * Created by ankit on 6/14/2017.
 */

public interface EditProfilePresenter {
    void validateProfile(Activity activity, EditProfileView editProfileView, String name, String email, String password, String profile_image);

}
