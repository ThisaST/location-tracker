package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Window;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;
import com.uok.se.thisara.smart.trackerapplication.model.BusRoute;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;

import java.util.ArrayList;
import java.util.List;

public class BusRouteActivity extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<BusRoute> busRouteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_route);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        initializeData();
        Window window = this.getWindow();

        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);
    }

    private void initializeAdapter() {

        mRecyclerView = findViewById(R.id.busRouteRecyclerView);
        BusRouteAdapter adapter = new BusRouteAdapter(busRouteList, BusRouteActivity.this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initializeData() {

        /*DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Log.d("firebase db reference", FirebaseAuth.getInstance().getCurrentUser().getUid());
        String path = "bus/"  + FirebaseAuth.getInstance().getCurrentUser().getUid();
        reference.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                setBusList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/

        busRouteList = new ArrayList<>();
        busRouteList.add(new BusRoute("138", "Kadawatha - Pettah", "Up"));
        busRouteList.add(new BusRoute("138", "Kadawatha - Pettah", "Down"));
        busRouteList.add(new BusRoute("200", "Gampaha - Pettah", "Up"));
        busRouteList.add(new BusRoute("200", "Gampaha - Pettah", "Down"));
        busRouteList.add(new BusRoute("154", "Kiribathgoda - Angulana", "Up"));
        busRouteList.add(new BusRoute("154", "Kiribathgoda - Angulana", "Down"));
        busRouteList.add(new BusRoute("514", "Weliweriya - Pettah", "Up"));
        busRouteList.add(new BusRoute("514", "Weliweriya - Pettah", "Down"));
        busRouteList.add(new BusRoute("138", "Kadawatha - Pettah", "Up"));
        busRouteList.add(new BusRoute("138", "Kadawatha - Pettah", "Up"));
        busRouteList.add(new BusRoute("138", "Kadawatha - Pettah", "Up"));
        busRouteList.add(new BusRoute("138", "Kadawatha - Pettah", "Up"));
        busRouteList.add(new BusRoute("138", "Kadawatha - Pettah", "Up"));

        initializeAdapter();

    }
}
