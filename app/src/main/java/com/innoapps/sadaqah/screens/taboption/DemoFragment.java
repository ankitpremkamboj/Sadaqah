package com.innoapps.sadaqah.screens.taboption;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoapps.sadaqah.R;

import butterknife.ButterKnife;

/**
 * Created by ankit on 6/7/2017.
 */

public class DemoFragment extends Fragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_login, container, false);

        ButterKnife.inject(this, view);
       // setFont();
        return view;

    }
}
