package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;

public class SingleBusViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_bus_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView busImageView = findViewById(R.id.busImageView);
        TextView registrationNumber = findViewById(R.id.registrationNumber);
        TextView routeNumber = findViewById(R.id.routeNumber);
        TextView routeName = findViewById(R.id.routeName);

        Intent intent = getIntent();
        Bus busDetails = (Bus) intent.getSerializableExtra("Bus");

        busImageView.setImageURI(Uri.parse(busDetails.getImageId()));

        Picasso.get()
                .load(Uri.parse(busDetails.getImageId()))
                .fit()
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .into(busImageView);
        registrationNumber.setText(busDetails.getRegistrationNo());
        routeName.setText("138");
        routeNumber.setText("Kadawatha- Colombo");
    }
}
