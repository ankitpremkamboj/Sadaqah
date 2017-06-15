package com.innoapps.sadaqah.screens.navigation;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.editprofile.EditProfileActivity;
import com.innoapps.sadaqah.screens.login.LoginActivity;
import com.innoapps.sadaqah.screens.navigation.model.UserDetailModel;
import com.innoapps.sadaqah.screens.navigation.presenter.UserDetailPresenter;
import com.innoapps.sadaqah.screens.navigation.presenter.UserDetailPresenterImpl;
import com.innoapps.sadaqah.screens.navigation.view.UserDetailView;
import com.innoapps.sadaqah.screens.signup.SignUpActivity;
import com.innoapps.sadaqah.screens.taboption.TabPagerAdapter;
import com.innoapps.sadaqah.utils.AppFonts;
import com.innoapps.sadaqah.utils.GlideCircleTransform;
import com.innoapps.sadaqah.utils.UserSession;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener, UserDetailView {

    @InjectView(R.id.imgMenu)
    ImageView imgMenu;

    @InjectView(R.id.drawer_layout)
    DrawerLayout drawer_layout;

    @InjectView(R.id.nav_view)
    NavigationView nav_view;
    LinearLayout lay_home;

    @InjectView(R.id.pager)
    ViewPager pager;
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;

    private TabPagerAdapter mAdapter;
    UserDetailPresenter userDetailPresenter;
    private ActionBar actionBar;
    UserSession userSession;
    TextView txt_email, txt_name, txt_home, txt_signup, txt_hou_to_use, txt_share_app, txt_rate_app, txt_news, txt_website, txt_logout;
    ImageView img_user, img_edit;


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

        setContentView(R.layout.activity_main);
        userSession = new UserSession(this);
        userDetailPresenter = new UserDetailPresenterImpl();
        userDetailPresenter.gettingUserDetails(this, this, userSession.getUserID());

        ButterKnife.inject(this);

        nav_view.setNavigationItemSelectedListener(this);
        manageHeaderLayout();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (userSession.isProRefresh()) {
            userSession.profileRefresh(false);
            userDetailPresenter.gettingUserDetails(this, this, userSession.getUserID());
        }
    }

    public void manageHeaderLayout() {

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.nav_home)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_history)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_setting)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_contact)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        pager.setAdapter(mAdapter);


        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));


        View headerView = nav_view.inflateHeaderView(R.layout.nav_header_main);


        txt_email = (TextView) headerView.findViewById(R.id.txt_email);
        txt_name = (TextView) headerView.findViewById(R.id.txt_name);

        txt_home = (TextView) headerView.findViewById(R.id.txt_home);
        txt_signup = (TextView) headerView.findViewById(R.id.txt_signup);
        txt_hou_to_use = (TextView) headerView.findViewById(R.id.txt_hou_to_use);
        txt_share_app = (TextView) headerView.findViewById(R.id.txt_share_app);
        txt_rate_app = (TextView) headerView.findViewById(R.id.txt_rate_app);
        txt_news = (TextView) headerView.findViewById(R.id.txt_news);
        txt_website = (TextView) headerView.findViewById(R.id.txt_website);
        txt_logout = (TextView) headerView.findViewById(R.id.txt_logout);

        img_user = (ImageView) headerView.findViewById(R.id.img_user);
        img_edit = (ImageView) headerView.findViewById(R.id.img_edit);
        lay_home = (LinearLayout) headerView.findViewById(R.id.lay_home);


        lay_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);
                }
                Intent intent = new Intent(NavigationActivity.this, EditProfileActivity.class);
                startActivity(intent);
            }
        });


        LinearLayout lay_logout = (LinearLayout) headerView.findViewById(R.id.lay_logout);
        lay_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showYesNoDialog();
            }
        });

        imgMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
                    drawer_layout.closeDrawer(GravityCompat.START);
                } else {
                    drawer_layout.openDrawer(GravityCompat.START);
                }


            }
        });
        setFont();

    }

    //Font set
    public void setFont() {
        txt_home.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_signup.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_hou_to_use.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_share_app.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_rate_app.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_news.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_website.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_logout.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_email.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        txt_name.setTypeface(Typeface.createFromAsset(this.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
    }

    //
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        pager.setCurrentItem(tab.getPosition());

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    public void showYesNoDialog() {
        try {

            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:

                            UserSession userSession = new UserSession(NavigationActivity.this);
                            userSession.clearUserSession();
                            Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                            break;

                        case DialogInterface.BUTTON_NEGATIVE:

                            break;
                    }
                }
            };

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to logout?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void getUserDetailsSuccessfull(UserDetailModel.Data data) {
        if (!data.getName().isEmpty() && data.getName() != null) {
            txt_name.setText(data.getName());
        } else {
            txt_name.setText("N/A");
        }
        if (!data.getEmail().isEmpty() && data.getEmail() != null) {
            txt_email.setText(data.getEmail());
        } else {
            txt_email.setText("N/A");
        }
        if (!data.getImage().isEmpty() && data.getImage() != null) {
            Glide.with(this)
                    .load(data.getImage())
                    .transform(new GlideCircleTransform(this))
                    .placeholder(R.mipmap.ic_default_profile)
                    .error(R.mipmap.ic_default_profile)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(img_user);
        }

    }

    @Override
    public void getUserDetailsUnSuccessful(String message) {

    }
}
