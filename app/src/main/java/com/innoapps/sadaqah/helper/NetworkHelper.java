package com.innoapps.sadaqah.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Braintech on 6/10/2016.
 */
public class NetworkHelper {

   public static boolean isNetworkAvaialble(Context context)
   {
       ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
       NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
       if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
           return true;
       else
           return false;
   }
}
