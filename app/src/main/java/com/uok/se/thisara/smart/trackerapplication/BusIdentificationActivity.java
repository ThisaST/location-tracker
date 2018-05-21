package com.uok.se.thisara.smart.trackerapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.uok.se.thisara.smart.trackerapplication.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusIdentificationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Bus> busList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_identification);

        mRecyclerView = findViewById(R.id.availableBusList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        
        initializeData();
        initializeAdapter();

    }

    private void initializeAdapter() {

        BusListAdapter adapter = new BusListAdapter(busList);
        mRecyclerView.setAdapter(adapter);
    }

    private void initializeData() {

        busList = new ArrayList<>();
        busList.add(new Bus("ABC4001", "Thisara Pramuditha", R.drawable.bus_icon_blue, "Laylend"));
        busList.add(new Bus("ABC4001", "Thisara Pramuditha", R.drawable.bus_icon_blue, "Laylend"));
        busList.add(new Bus("ABC4001", "Thisara Pramuditha", R.drawable.bus_icon_blue, "Laylend"));
        busList.add(new Bus("ABC4001", "Thisara Pramuditha", R.drawable.bus_icon_blue, "Laylend"));
    }
}
