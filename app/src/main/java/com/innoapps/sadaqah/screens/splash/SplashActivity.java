package com.innoapps.sadaqah.screens.splash;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.login.LoginActivity;
import com.innoapps.sadaqah.screens.navigation.NavigationActivity;
import com.innoapps.sadaqah.utils.UserSession;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 2000;
    UserSession userSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                userSession = new UserSession(SplashActivity.this);
                if (userSession.isUserLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }


/*
                // This method will be executed once the timer is over

              */
/*  Intent intent = new Intent(SplashActivity.this,ProfileActivity.class);*//*
                userSession = new UserSession(SplashActivity.this);
                if (userSession.isUserLoggedIn()) {
                    Intent intent = new Intent(SplashActivity.this, NavigationActivity.class);
                    startActivity(intent);
                    finish();


                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }*/


            }
        }, SPLASH_TIME_OUT);
    }
}
