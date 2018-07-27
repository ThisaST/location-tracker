package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;
import com.uok.se.thisara.smart.trackerapplication.ui.riderui.RiderActivity;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;
import com.uok.se.thisara.smart.trackerapplication.viewmodel.BusListViewModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import dmax.dialog.SpotsDialog;

public class BusIdentificationActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<Bus> busList;
    private Button addNewBusButton;
    private SpotsDialog waitingDialog;
    private BusListViewModel busListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_identification);

        //waitingTimeToLoadData();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //mRecyclerView.setLayoutManager(linearLayoutManager);

        addNewBusButton = findViewById(R.id.addNewBusButton);

        /*addNewBusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BusIdentificationActivity.this, AddNewBusActivity.class);
                startActivity(intent);
                BusIdentificationActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }
        });*/

        FloatingActionButton floatingActionButton = findViewById(R.id.addNewBusFloatingButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BusIdentificationActivity.this, AddNewBusActivity.class);
                startActivity(intent);
                BusIdentificationActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        /*Button mainMenuButton = findViewById(R.id.mainMenu);

        mainMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(BusIdentificationActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });*/

        waitingDialog = new SpotsDialog(BusIdentificationActivity.this);
        waitingDialog.show();

        initializeData();
        /*busListViewModel = ViewModelProviders.of(this).get(BusListViewModel.class);
        addDataToList(busListViewModel.getBusList());*/

        Window window = this.getWindow();

        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);

    }


    private void initializeAdapter() {

        mRecyclerView = findViewById(R.id.availableBusList);
        BusListAdapter adapter = new BusListAdapter(busList, BusIdentificationActivity.this);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        waitingDialog.dismiss();
    }

    private void initializeData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
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
        });


        //addDataToList(busListViewModel.getBusList());


    }

    private void setBusList(DataSnapshot dataSnapshot) {

        HashMap<String, Object> busValues = (HashMap<String, Object>) dataSnapshot.getValue();

        Bus newBus = new Bus();
        newBus.setBusModel(busValues.get("busModel").toString());
        newBus.setRegistrationNo(busValues.get("registrationNo").toString());
        newBus.setOwnerName(busValues.get("ownerName").toString());
        newBus.setImageId(Integer.parseInt(busValues.get("imageId").toString()));



        busList = new ArrayList<>();
        busList.add(newBus);
        busList.add(new Bus("wpXA-1234", "Nilupul Shenal", 2, "LEYLAND"));
        int size= busList.size();
        System.out.println(size);

        initializeAdapter();

    }

    /*public void addDataToList(LiveData<List<Bus>> busList) {

        this.busList = busList.getValue();

        initializeAdapter();
    }*/

}
