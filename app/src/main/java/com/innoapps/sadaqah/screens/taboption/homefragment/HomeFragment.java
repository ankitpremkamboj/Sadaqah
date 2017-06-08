package com.innoapps.sadaqah.screens.taboption.homefragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.innoapps.sadaqah.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by ankit on 6/7/2017.
 */

public class HomeFragment extends Fragment {

    @InjectView(R.id.gridView)
    GridView gridView;
    @InjectView(R.id.lay)
    LinearLayout lay;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        ButterKnife.inject(this, view);

        // setFont();
        return view;

    }

    @OnClick(R.id.lay)
    public void dialog() {
        showDialog();
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.home_diaolog);


        dialog.show();

    }
}