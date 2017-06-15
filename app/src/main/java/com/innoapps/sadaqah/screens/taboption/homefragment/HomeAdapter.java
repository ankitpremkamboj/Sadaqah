package com.innoapps.sadaqah.screens.taboption.homefragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.taboption.homefragment.model.HomeModel;
import com.innoapps.sadaqah.screens.taboption.homefragment.presenter.HomePresenter;
import com.innoapps.sadaqah.utils.AppFonts;
import com.innoapps.sadaqah.utils.UserSession;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import java.util.ArrayList;

/**
 * Created by ankit on 6/7/2017.
 */

public class HomeAdapter extends BaseAdapter {

    Context context;
    ArrayList<HomeModel.Datum> datumArrayList;
    DisplayImageOptions options;
    HomePresenter homePresenter;
    UserSession userSession;

    public HomeAdapter(Context context, ArrayList<HomeModel.Datum> datumArrayList, HomePresenter homePresenter) {
        this.context = context;
        this.datumArrayList = datumArrayList;
        this.homePresenter = homePresenter;
        userSession = new UserSession(context);

        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.login_logo)
                .showImageForEmptyUri(R.mipmap.login_logo)
                .showImageOnFail(R.mipmap.login_logo)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .considerExifParams(true)
                .build();

    }


    @Override
    public int getCount() {
        return datumArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator;
        ViewHolder viewHolder;
        if (convertView == null) {
            inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflator.inflate(R.layout.home_fragment_item, null);
            viewHolder = new ViewHolder(convertView);
            //convertView.setTag(viewHolder.rl_dialog);
            convertView.setTag(viewHolder);
            // convertView.setTag(viewHolder.rl_dialog);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }
        populateData(viewHolder, position);

        return convertView;

    }


    public void populateData(ViewHolder viewHolder, final int pos) {
        try {

            setFont(viewHolder);
            final String _id = datumArrayList.get(pos).getId();
            String _provider = datumArrayList.get(pos).getProvider();
            String _donation_type = datumArrayList.get(pos).getDonationType();
            final  String _amount = datumArrayList.get(pos).getAmount();
            final String _title = datumArrayList.get(pos).getTitle();
            String _logo = datumArrayList.get(pos).getImage();
            //String _report = datumArrayList.get(pos).getReport();

            if (!_provider.isEmpty() && _provider != null) {
                viewHolder.txt_company_name.setText(_title);
            } else {
                viewHolder.txt_company_name.setText("N/A");
            }
            if (!_donation_type.isEmpty() && _donation_type != null) {

                viewHolder.txt_company_charity.setText(_donation_type);
            } else {
                viewHolder.txt_company_charity.setText("N/A");
            }
            if (!_amount.isEmpty() && _amount != null) {

                viewHolder.txt_rs.setText(_amount);
                viewHolder.txt_msg.setText(_amount);
            } else {
                viewHolder.txt_msg.setText("N/A");
            }
            if (!_title.isEmpty() && _title != null) {

                // viewHolder.txt_rs.setText(_title);
            }


            try {

                if (_logo != null & !_logo.isEmpty()) {
                    ImageLoader imageLoader = ImageLoader.getInstance(); // Get singleton instance
                    imageLoader.init(ImageLoaderConfiguration.createDefault(context));

                    imageLoader.displayImage(_logo, viewHolder.logo_img, options);
                    imageLoader.loadImage(_logo, new SimpleImageLoadingListener() {
                        @Override
                        public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                            // Do whatever you want with Bitmap
                        }
                    });
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            //  Picasso.with(context).load(_logo).placeholder(R.mipmap.login_logo).centerCrop().into(viewHolder.logo_img);
/*

            Glide.with(context)
                    .load(_logo)
                    .crossFade()
                    .error(R.mipmap.login_logo)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .fitCenter()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(viewHolder.logo_img);
*/

/*
            Glide.with(context)
                    .load(_logo)
                  //  .transform(new GlideCircleTransform(this))
                   // .placeholder(R.mipmap.login_logo)
                    .error(R.mipmap.login_logo)
                   // .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(viewHolder.logo_img);*/

            viewHolder.rl_dialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //int tag = Integer.parseInt(v.getTag().toString());
                    //HomeModel.Datum datum = datumArrayList.get(tag);

                    showDialog(_title, _amount, _id);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setFont(ViewHolder viewHolder) {

        viewHolder.txt_company_name.setTypeface(Typeface.createFromAsset(context.getAssets(), AppFonts.MONTSERRAT_ARABIC_SEMIBOLD));
        viewHolder.txt_company_charity.setTypeface(Typeface.createFromAsset(context.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        viewHolder.txt_curency.setTypeface(Typeface.createFromAsset(context.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));
        viewHolder.txt_rs.setTypeface(Typeface.createFromAsset(context.getAssets(), AppFonts.MONTSERRAT_ARABIC_BOLD));
        viewHolder.txt_msg.setTypeface(Typeface.createFromAsset(context.getAssets(), AppFonts.MONTSERRAT_ARABIC_REGULAR));

    }

    static class ViewHolder {
        private ImageView logo_img, img_donation, img_arrow;
        private TextView txt_company_name, txt_company_charity, txt_curency, txt_rs, txt_msg;
        private RelativeLayout rl_dialog;

        ViewHolder(View view) {

            logo_img = (ImageView) view.findViewById(R.id.logo_img);
            img_donation = (ImageView) view.findViewById(R.id.img_donation);
            img_arrow = (ImageView) view.findViewById(R.id.img_arrow);

            rl_dialog = (RelativeLayout) view.findViewById(R.id.rl_dialog);
            txt_company_name = (TextView) view.findViewById(R.id.txt_company_name);
            txt_company_charity = (TextView) view.findViewById(R.id.txt_company_charity);
            txt_curency = (TextView) view.findViewById(R.id.txt_curency);
            txt_rs = (TextView) view.findViewById(R.id.txt_rs);
            txt_msg = (TextView) view.findViewById(R.id.txt_msg);


        }
    }

    private void showDialog(String title, final String amount, final String id) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.home_diaolog);
        TextView txt_title = (TextView) dialog.findViewById(R.id.txt_title);
        TextView txt_yes = (TextView) dialog.findViewById(R.id.txt_yes);
        TextView txt_no = (TextView) dialog.findViewById(R.id.txt_no);
        txt_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (!title.isEmpty() && title != null) {
            txt_title.setText(title);
        } else {
            txt_title.setText("N/A");
        }

        txt_yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // homePresenter.callAddDonation(userSession.getUserID(), id, amount);
                dialog.dismiss();
            }
        });
        dialog.show();

    }
}
