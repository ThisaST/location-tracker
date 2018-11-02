package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

/**
 * Created by malware on 5/11/18.
 */

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.BusViewHolder> {


     public static class BusViewHolder extends RecyclerView.ViewHolder {

        LinearLayout singleView;
        TextView registrationNo;
        TextView ownerName;
        TextView busModel;
        ImageView busImage;


        BusViewHolder(View busView) {
            super(busView);

            singleView = busView.findViewById(R.id.singleLayout);
            registrationNo = busView.findViewById(R.id.busRegistrationNo);
            ownerName = busView.findViewById(R.id.ownerName);
            busModel = busView.findViewById(R.id.busModel);
            busImage = busView.findViewById(R.id.busImage);

        }

    }

    /*public static Bus getSelectedBus() {

        return new BusListAdapter().selectedBus;
    }*/

    private List<Bus> busList;
    private Bus selectedBus;
    private Context mContext;

    BusListAdapter() {

    }

    public BusListAdapter(List<Bus> busList, Context mContext) {

        this.busList = busList;
        this.mContext = mContext;
    }

    @Override
    public BusListAdapter.BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bus_view, parent, false);
        BusViewHolder busViewHolder = new BusViewHolder(view);
        return busViewHolder;
    }

    @Override
    public void onBindViewHolder(BusListAdapter.BusViewHolder holder, final int position) {

        holder.registrationNo.setText(busList.get(position).getRegistrationNo());
        holder.busModel.setText(busList.get(position).getBusModel());
        holder.ownerName.setText(busList.get(position).getOwnerName());

        Picasso.get()
                .load(R.drawable.bus_test)
                .fit()
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .into(holder.busImage);

        /*selectedBus.setRegistrationNo(busList.get(position).getRegistrationNo());
        selectedBus.setBusModel(busList.get(position).getBusModel());
        selectedBus.setOwnerName(busList.get(position).getOwnerName());
        selectedBus.setImageId(busList.get(position).getImageId());*/

        holder.singleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //create an object to pass the bus to the main activity
                Bus selectedBus = new Bus(busList.get(position).getRegistrationNo(), busList.get(position).getOwnerName(), busList.get(position).getImageId(), busList.get(position).getBusModel());

                Intent intent = new Intent(mContext, SingleBusViewActivity.class);
                intent.putExtra("Bus", selectedBus);
                mContext.startActivity(intent);

                Toast.makeText(mContext,busList.get(position).getRegistrationNo(), Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public int getItemCount() {
        int busListlength = 0;

        if (busList != null) {
             busListlength = busList.size();
        }

        return busListlength;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


}
