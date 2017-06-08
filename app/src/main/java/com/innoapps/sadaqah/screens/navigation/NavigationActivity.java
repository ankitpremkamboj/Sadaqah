package com.innoapps.sadaqah.screens.navigation;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Configuration;
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

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.login.LoginActivity;
import com.innoapps.sadaqah.screens.taboption.TabPagerAdapter;

import java.util.Locale;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,TabLayout.OnTabSelectedListener {

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
    private ActionBar actionBar;

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


        ButterKnife.inject(this);

        nav_view.setNavigationItemSelectedListener(this);
        manageHeaderLayout();


    }

    public void manageHeaderLayout() {

        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.nav_home)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_history)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_setting)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tab_contact)));

        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        mAdapter = new TabPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());

        //Adding adapter to pager
        pager.setAdapter(mAdapter);

        //Adding onTabSelectedListener to swipe views
        //tabLayout.setOnTabSelectedListener(this);

        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(pager));


/*
        actionBar = getActionBar();
        mAdapter = new TabPagerAdapter(getSupportFragmentManager());

        String[] tabs = {"ankit", "kumar", "kamboj"};

        pager.setAdapter(mAdapter);*/

      //  actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);



        View headerView = nav_view.inflateHeaderView(R.layout.nav_header_main);
        lay_home = (LinearLayout) headerView.findViewById(R.id.lay_home);
        lay_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                startActivity(intent);
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
}
