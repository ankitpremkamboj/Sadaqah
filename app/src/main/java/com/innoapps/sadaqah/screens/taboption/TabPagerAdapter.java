package com.innoapps.sadaqah.screens.taboption;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.innoapps.sadaqah.screens.contact.ContactFragment;
import com.innoapps.sadaqah.screens.history.HistoryFragment;
import com.innoapps.sadaqah.screens.setting.SettingFragment;
import com.innoapps.sadaqah.screens.taboption.homefragment.HomeFragment;

/**
 * Created by ankit on 6/7/2017.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;

    public TabPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }


//Add fragment screen
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 1:
                HistoryFragment historyFragment = new HistoryFragment();
                return historyFragment;
            case 2:
                SettingFragment settingFragment = new SettingFragment();
                return settingFragment;
            case 3:
                ContactFragment contactFragment = new ContactFragment();
                return contactFragment;
            default:
                return null;
        }
    }
//Tab count
    @Override
    public int getCount() {
        return tabCount;
    }
}
