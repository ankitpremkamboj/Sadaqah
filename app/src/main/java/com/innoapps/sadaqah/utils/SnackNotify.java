package com.innoapps.sadaqah.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.innoapps.sadaqah.interfaces.OnClickInterface;


/**
 * Created by Braintech on 30-Oct-15.
 */
public class SnackNotify {

    // check for internet connection
    public static void checkConnection(final OnClickInterface onRetryClick, View coordinatorLayout) {

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "No internet connection!", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRetryClick.onClick();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        // Fonts.robotoRegular(activity, textView);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    // Back button press
    public static void showSnakeBarForBackPress(View coordinatorLayout) {
        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, "Please click BACK again to exit.", Snackbar.LENGTH_LONG);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        // Fonts.robotoRegular(activity, textView);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();

    }

    // for showing the message in snack bar
    public static void showMessage(String msg, View coordinatorLayout) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, msg, Snackbar.LENGTH_LONG);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        // Fonts.robotoRegular(activity, textView);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();

    }
      //getting the permission
    public static void sGetPermission(String msg, String btnName, final OnClickInterface onRetryClick, View coordinatorLayout) {

        Snackbar snackbar = Snackbar
                .make(coordinatorLayout, msg, Snackbar.LENGTH_INDEFINITE)
                .setAction(btnName, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRetryClick.onClick();
                    }
                });

        // Changing message text color
        snackbar.setActionTextColor(Color.RED);

        // Changing action button text color
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        // Fonts.robotoRegular(activity, textView);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }


}
