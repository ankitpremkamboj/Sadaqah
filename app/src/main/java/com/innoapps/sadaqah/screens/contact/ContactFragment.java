package com.innoapps.sadaqah.screens.contact;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innoapps.sadaqah.R;

import butterknife.ButterKnife;

/**
 * Created by ankit on 6/16/2017.
 */

public class ContactFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contact_us_fragment, container, false);

        ButterKnife.inject(this, view);

        // setFont();
        return view;

    }
}
