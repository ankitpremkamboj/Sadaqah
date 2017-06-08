package com.innoapps.sadaqah.screens.login;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.innoapps.sadaqah.R;

import java.util.Locale;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


     /*   String languageToLoad = "ar"; // your language you can get this as an input or save it in a file ,or do as your requirement
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
*/
        setContentView(R.layout.activity_login);
    }
}
