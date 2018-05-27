package com.uok.se.thisara.smart.trackerapplication;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.uok.se.thisara.smart.trackerapplication.model.Bus;

import java.util.List;

/**
 * Created by malware on 5/11/18.
 */

public class BusListAdapter extends RecyclerView.Adapter<BusListAdapter.BusViewHolder> {


    public static class BusViewHolder extends RecyclerView.ViewHolder {


        CardView cardView;
        TextView registrationNo;
        TextView ownerName;
        TextView busModel;
        ImageView busImage;

        BusViewHolder(View busView) {
            super(busView);

            cardView = busView.findViewById(R.id.busCardView);
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

    BusListAdapter() {

    }

    public BusListAdapter(List<Bus> busList) {

        this.busList = busList;
    }

    @Override
    public BusListAdapter.BusViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bus_view, parent, false);
        BusViewHolder busViewHolder = new BusViewHolder(view);
        return busViewHolder;
    }

    @Override
    public void onBindViewHolder(BusListAdapter.BusViewHolder holder, int position) {

        holder.registrationNo.setText(busList.get(position).getRegistrationNo());
        holder.busModel.setText(busList.get(position).getBusModel());
        holder.ownerName.setText(busList.get(position).getOwnerName());
        holder.busImage.setImageResource(busList.get(position).getImageId());

        /*selectedBus.setRegistrationNo(busList.get(position).getRegistrationNo());
        selectedBus.setBusModel(busList.get(position).getBusModel());
        selectedBus.setOwnerName(busList.get(position).getOwnerName());
        selectedBus.setImageId(busList.get(position).getImageId());*/

    }

    @Override
    public int getItemCount() {
        return busList.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
