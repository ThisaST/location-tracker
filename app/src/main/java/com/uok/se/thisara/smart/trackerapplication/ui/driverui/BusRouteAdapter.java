package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;
import com.uok.se.thisara.smart.trackerapplication.model.BusRoute;

import java.util.List;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class BusRouteAdapter extends RecyclerView.Adapter<BusRouteAdapter.BusRouteViewHolder>{


    public static class BusRouteViewHolder extends RecyclerView.ViewHolder {

        LinearLayout singleView;
        TextView routeNumber;
        TextView routeName;
        TextView upOrDown;
        ImageView busRouteImage;


        BusRouteViewHolder(View busView) {
            super(busView);

            singleView = busView.findViewById(R.id.busRouteLayout);
            routeNumber = busView.findViewById(R.id.busRouteNumber);
            routeName = busView.findViewById(R.id.busRouteName);
            upOrDown = busView.findViewById(R.id.upOrDown);
            busRouteImage = busView.findViewById(R.id.busRouteImage);

        }

    }

    private List<BusRoute> busRouteList;
    private BusRoute selectedRoute;
    private Context mContext;

    public BusRouteAdapter(List<BusRoute> busRoute, Context mContext) {

        this.busRouteList = busRoute;
        this.mContext = mContext;
    }

    @Override
    public BusRouteAdapter.BusRouteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bus_route_view, parent, false);
        BusRouteViewHolder busViewHolder = new BusRouteViewHolder(view);
        return busViewHolder;
    }

    @Override
    public void onBindViewHolder(BusRouteAdapter.BusRouteViewHolder holder, int position) {

        holder.routeNumber.setText(busRouteList.get(position).getRouteNumber());
        holder.routeName.setText(busRouteList.get(position).getRouteName());
        if (busRouteList.get(position).getUpOrDown().equalsIgnoreCase("Up")) {

            holder.upOrDown.setText(busRouteList.get(position).getUpOrDown());
        }else if (busRouteList.get(position).getUpOrDown().equalsIgnoreCase("Down")) {

            holder.upOrDown.setText(busRouteList.get(position).getUpOrDown());
        }

        if (busRouteList.get(position).getRouteNumber().equalsIgnoreCase("138")) {

            Picasso.get()
                    .load(R.drawable.ic_busroute_138)
                    .fit()
                    .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                    .into(holder.busRouteImage);
        }else if (busRouteList.get(position).getRouteNumber().equalsIgnoreCase("200")) {

            Picasso.get()
                    .load(R.drawable.ic_busroute_138)
                    .fit()
                    .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                    .into(holder.busRouteImage);
        }

    }

    @Override
    public int getItemCount() {
        int busListlength = 0;

        if (busRouteList != null) {
            busListlength = busRouteList.size();
        }

        return busListlength;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
