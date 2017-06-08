package com.innoapps.sadaqah.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * Created by Braintech on 7/22/2015.
 */
public class UserSession {
    SharedPreferences _pref;
    SharedPreferences.Editor _editor;
    Context context;
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "user_pref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "is_user_logged_in";

    public static final String KEY_USER_ID = "user_id";
    public static final String KEY_USER_NAME = "name";
    public static final String KEY_USER_EMAIL = "email";
    public static final String KEY_USER_MOBILE = "mobile";//
    public static final String KEY_USER_TOKENID = "token_id";
    public static final String KEY_USER_IMAGE = "user_image";
    public static final String KEY_REFRESH = "refresh";
    public static final String KEY_PROREFRESH = "prorefresh";


    public UserSession(Context _context) {
        this.context = _context;
        _pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        _editor = _pref.edit();
    }

    /**
     * Create login session
     */
    public void createUserSession(String user_id, String user_name, String email, String mobile, int token_id, String userImage) {

        _editor.putString(KEY_USER_ID, user_id);
        _editor.putString(KEY_USER_NAME, user_name);
        _editor.putString(KEY_USER_EMAIL, email);
        _editor.putBoolean(IS_LOGIN, true);
        _editor.putString(KEY_USER_MOBILE, mobile);
        _editor.putInt(KEY_USER_TOKENID, token_id);
        _editor.putString(KEY_USER_IMAGE, userImage);

        // commit changes
        _editor.commit();
    }

    public void createUserID(String user_id) {

        _editor.putString(KEY_USER_ID, user_id);
        _editor.putBoolean(IS_LOGIN, true);
        // commit changes
        _editor.commit();
    }

    public void refresh(boolean value) {
        _editor.putBoolean(KEY_REFRESH, value);
        _editor.commit();
    }

    public void profileRefresh(boolean value) {
        _editor.putBoolean(KEY_PROREFRESH, value);
        _editor.commit();
    }

    /* Get KEY_USER_ID */
    public String getUserID() {
        return _pref.getString(KEY_USER_ID, "");
    }

    /* Get KEY_USER_NAME */
    public String getUserName() {
        return _pref.getString(KEY_USER_NAME, "");
    }

    /* Get KEY_USER_EMAIL */
    public String getUserEmail() {
        return _pref.getString(KEY_USER_EMAIL, "");
    }

    /* Get KEY_USER_MOBILE */
    public String getUserMobile() {
        return _pref.getString(KEY_USER_MOBILE, "");
    }

    /* Get KEY_USER_TOKENID */
    public int getUserTokenid() {
        return _pref.getInt(KEY_USER_TOKENID, 0);
    }


    /* Get KEY_USER_TOKENID */
    public String getUserImage() {
        return _pref.getString(KEY_USER_IMAGE, "");
    }

    /**
     * Clear session details
     */

    public void clearUserSession() { // Clearing all data from Shared
        _editor.clear();
        _editor.commit();
    }


    // Get Login State
    public boolean isUserLoggedIn() {
        return _pref.getBoolean(IS_LOGIN, false);
    }

    public boolean isRefresh() {
        return _pref.getBoolean(KEY_REFRESH, false);
    }

    public boolean isProRefresh() {
        return _pref.getBoolean(KEY_PROREFRESH, false);
    }

}
