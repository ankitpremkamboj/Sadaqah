package com.innoapps.sadaqah.screens.login;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.login.model.LoginModel;
import com.innoapps.sadaqah.screens.login.presenter.LoginPresenter;
import com.innoapps.sadaqah.screens.login.presenter.LoginPresenterImpl;
import com.innoapps.sadaqah.screens.login.view.LoginView;
import com.innoapps.sadaqah.screens.navigation.NavigationActivity;
import com.innoapps.sadaqah.screens.signup.SignUpActivity;
import com.innoapps.sadaqah.utils.AppFonts;
import com.innoapps.sadaqah.utils.CheckPermissions;
import com.innoapps.sadaqah.utils.Utils;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView {

    @InjectView(R.id.txt_welcome)
    TextView txt_welcome;
    @InjectView(R.id.txt_sign_in)
    TextView txt_sign_in;
    @InjectView(R.id.input_email)
    EditText input_email;
    @InjectView(R.id.input_password)
    EditText input_password;
    @InjectView(R.id.txt_forgot_password)
    TextView txt_forgot_password;
    @InjectView(R.id.btn_login)
    Button btn_login;
    @InjectView(R.id.txt_new_user_sign_up)
    TextView txt_new_user_sign_up;
    @InjectView(R.id.txt_signup)
    TextView txt_signup;
    @InjectView(R.id.txt_or)
    TextView txt_or;
    @InjectView(R.id.txt_google)
    TextView txt_google;
    @InjectView(R.id.txt_fb)
    TextView txt_fb;
    @InjectView(R.id.lay_fb)
    RelativeLayout lay_fb;
    @InjectView(R.id.lay_google)
    RelativeLayout lay_google;

    LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String languageToLoad = "ar"; // your language you can get this as an input or save it in a file ,or do as your requirement
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        setContentView(R.layout.activity_login);
        CheckPermissions.checkPermissions(this);
        ButterKnife.inject(this);
        loginPresenter = new LoginPresenterImpl();
        setFonts();

    }

    private void setFonts() {


        txt_welcome.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_SEMIBOLD));
        txt_sign_in.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        input_email.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        input_password.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_forgot_password.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        btn_login.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_new_user_sign_up.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_signup.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_or.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_google.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_fb.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));

    }

    @OnClick(R.id.btn_login)
    public void login() {
        String _email = input_email.getText().toString().trim();
        String _password = input_password.getText().toString().trim();

        loginPresenter.validateLogin(_email, _password, this, this);
    }

    @OnClick(R.id.txt_signup)
    public void signUp() {
        Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onEmailError(String email) {
        Utils.showError(input_email, email, this);

    }

    @Override
    public void onPasswordError(String password) {
        Utils.showError(input_email, password, this);

    }

    @Override
    public void onLoginSuccess(LoginModel.Data data) {
        Intent intent = new Intent(LoginActivity.this, NavigationActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onLoginUnSuccessful(String msg) {

//

    }

    @Override
    public void onLoginInternetError() {

    }

}
