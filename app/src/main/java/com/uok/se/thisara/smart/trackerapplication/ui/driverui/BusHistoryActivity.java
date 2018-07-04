package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;

import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;

public class BusHistoryActivity extends AppCompatActivity {

    BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_history);


        mBottomNavigationView = findViewById(R.id.bottomNavigationView);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;
                switch (item.getItemId()) {
                    case R.id.distanceItem:
                        selectedFragment = DistanceTravelFragment.newInstance();
                        break;
                    case R.id.durationItem:
                        selectedFragment = TravelDurationFragment.newInstance();
                        break;
                    case R.id.breakdownItem:
                        selectedFragment = BreakdownDetailFragment.newInstance();
                        break;
                }
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragmentContainer, selectedFragment);
                transaction.commit();
                return true;
            }
        });

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, DistanceTravelFragment.newInstance());
        transaction.commit();

        Window window = this.getWindow();

        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);
    }
}
