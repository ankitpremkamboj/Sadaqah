package com.innoapps.sadaqah.screens.taboption;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.innoapps.sadaqah.screens.taboption.homefragment.HomeFragment;

/**
 * Created by ankit on 6/7/2017.
 */

public class TabPagerAdapter extends FragmentStatePagerAdapter {
    int tabCount;

    public TabPagerAdapter(FragmentManager fm,int tabCount) {
        super(fm);
        this.tabCount= tabCount;
    }


//Add fragment screen
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                DemoFragment tab2 = new DemoFragment();
                return tab2;
            case 2:
                DemoFragment tab3 = new DemoFragment();
                return tab3;
            case 3:
                DemoFragment tab4 = new DemoFragment();
                return tab4;
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
