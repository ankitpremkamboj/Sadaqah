package com.innoapps.sadaqah.screens.history;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.innoapps.sadaqah.R;
import com.innoapps.sadaqah.screens.history.model.HistoryModel;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by ankit on 7/25/2017.
 */

public class HistoryAdapter extends  RecyclerView.Adapter<HistoryAdapter.Holder> {

Context  context;
    ArrayList<HistoryModel.Datum> historyListArrayList;


    public HistoryAdapter(Context context, ArrayList<HistoryModel.Datum> historyListArrayList) {
        this.context = context;
        this.historyListArrayList = historyListArrayList;
    }




    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.history_fragment_item, null);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, int position) {


        final HistoryModel.Datum historyModel= historyListArrayList.get(position);


        String donation_name=historyModel.getDonationName();
        String provider=historyModel.getProvider();
        String donation_type=historyModel.getDonationType();
        String brand_logo=historyModel.getBrandLogo();
        String amount=historyModel.getAmount();
        String donation_id=historyModel.getDonationId();


        if (!amount.isEmpty()&&amount!=null){
           // holder.txt_amount.setText(amount);
            holder.txtAmount.setText(amount);

        }else {
           // holder.txtAmount.setText("N/A");
            holder.txt_amount.setText("N/A");

        }
        if (!donation_id.isEmpty()&&donation_id!=null){
            holder.txt_amount.setText(donation_id);

        }else {
            holder.txt_amount.setText("N/A");

        }
        if (!brand_logo.isEmpty()&&brand_logo!=null){

            Glide.with(context)
                    .load(brand_logo)
                   // .transform(new GlideCircleTransform(context))
                    .placeholder(R.mipmap.du)
                    .error(R.mipmap.du)
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(holder.img_logo);

        }
        if (!donation_type.isEmpty()&&donation_type!=null){
            holder.txt_donation_type.setText(donation_type);

        }else {
            holder.txt_donation_type.setText("N/A");

        }
        if (!provider.isEmpty()&&provider!=null){
           // holder.txt_curncy.setText(provider);

        }else {
          //  holder.txt_curncy.setText("N/A");

        }
        if (!donation_name.isEmpty()&&donation_name!=null){
            holder.txt_donation_name.setText(donation_name);

        }else {
            holder.txt_donation_name.setText("N/A");

        }
       /* if (!provider.isEmpty()&&provider!=null){
            holder.txt_donation_name.setText(provider);

        }else {
            holder.txt_donation_name.setText("N/A");

        }
*/

    }

    @Override
    public int getItemCount() {
        return historyListArrayList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

      /*  @InjectView(R.id.imageView)
        ImageView imageView;*/

        @InjectView(R.id.txt_amount)
        TextView txt_amount;

        @InjectView(R.id.txt_curncy)
        TextView txt_curncy;

        @InjectView(R.id.txt_donation_name)
        TextView txt_donation_name;

        @InjectView(R.id.txt_donation_type)
        TextView txt_donation_type;

        @InjectView(R.id.txtDay)
        TextView txtDay;

        @InjectView(R.id.img_logo)
        ImageView img_logo;

        @InjectView(R.id.txtAmount)
        TextView txtAmount;

       /* @InjectView(R.id.location_image)
        ImageView location_image;

        @InjectView(R.id.event_image)
        ImageView event_image;
        @InjectView(R.id.progress)
        ProgressBar progress;

        @InjectView(R.id.txt_location)
        TextView txt_location;
        @InjectView(R.id.txt_eventName)
        TextView txt_eventName;
        @InjectView(R.id.content_main)
        RelativeLayout content_main;*/


        public Holder(View itemView) {
            super(itemView);

            ButterKnife.inject(this, itemView);
        }
    }


}
