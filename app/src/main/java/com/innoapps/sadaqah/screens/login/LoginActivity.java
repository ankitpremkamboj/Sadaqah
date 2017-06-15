package com.innoapps.sadaqah.screens.login;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookAuthorizationException;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.login.model.LoginModel;
import com.innoapps.sadaqah.screens.login.presenter.LoginPresenter;
import com.innoapps.sadaqah.screens.login.presenter.LoginPresenterImpl;
import com.innoapps.sadaqah.screens.login.view.LoginView;
import com.innoapps.sadaqah.screens.navigation.NavigationActivity;
import com.innoapps.sadaqah.screens.signup.SignUpActivity;
import com.innoapps.sadaqah.utils.AppFonts;
import com.innoapps.sadaqah.utils.CheckPermissions;
import com.innoapps.sadaqah.utils.SnackNotify;
import com.innoapps.sadaqah.utils.Utils;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements LoginView, GoogleApiClient.OnConnectionFailedListener {
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
    @InjectView(R.id.coordinateLayout)
    LinearLayout coordinateLayout;

    LoginPresenter loginPresenter;
    private CallbackManager callbackManager;
    GoogleApiClient mGoogleApiClient;

    private static final int RC_SIGN_IN = 007;
    private static final String TAG = LoginActivity.class.getSimpleName();


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
        // fbkeyhash();
        FacebookSdk.sdkInitialize(getApplicationContext());

        // Configure sign-in to request the user's ID, email address, and basic
// profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleApiClient with access to the Google Sign-In API and the
// options specified by gso.
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


    }


    private void fbkeyhash() {

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.innoapps.sadaqah", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.i("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @OnClick(R.id.lay_fb)
    public void facebooklogin() {
        openFacebookLogin();

    }

    @OnClick(R.id.lay_google)
    public void googleLogin() {
        openGoogleLogin();
    }

    private void openGoogleLogin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);


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
        SnackNotify.showMessage(msg, coordinateLayout);


    }

    @Override
    public void onLoginInternetError() {
        SnackNotify.showMessage(getString(R.string.internet_check), coordinateLayout);

    }


    public void openFacebookLogin() {
        if (AccessToken.getCurrentAccessToken() != null) {
            LoginManager.getInstance().logOut();
        }
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, (Arrays.asList("public_profile", "user_friends", "user_birthday", "user_about_me", "email")));
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("tag", "FF fb onSuccess");
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            Log.e("Response", "" + object);
                            //  ((LoginFragment) loginFragment).onLoginCompleted(object);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }


                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,link,birthday,picture,email,gender");
                request.setParameters(parameters);
                request.executeAsync();


            }


            @Override
            public void onCancel() {
                Log.d("tag", "fb onCancel");
                if (AccessToken.getCurrentAccessToken() != null) {
                    LoginManager.getInstance().logOut();
                }
            }


            @Override
            public void onError(FacebookException exception) {
                if (exception instanceof FacebookAuthorizationException) {
                    if (AccessToken.getCurrentAccessToken() != null) {
                        LoginManager.getInstance().logOut();
                    }
                }
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (callbackManager != null) {
                callbackManager.onActivityResult(requestCode, resultCode, data);
            }

            if (requestCode == RC_SIGN_IN) {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                handleSignInResult(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();

            Log.e(TAG, "display name: " + acct.getDisplayName());

            String personName = acct.getDisplayName();
            String personPhotoUrl = acct.getPhotoUrl().toString();
            String email = acct.getEmail();

            Log.e(TAG, "Name: " + personName + ", email: " + email
                    + ", Image: " + personPhotoUrl);

/*          txtName.setText(personName);
            txtEmail.setText(email);
            Glide.with(getApplicationContext()).load(personPhotoUrl)
                    .thumbnail(0.5f)
                    .crossFade()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(imgProfilePic);*/

            //updateUI(true);
        } else {
            // Signed out, show unauthenticated UI.
            // updateUI(false);
        }
    }

}
