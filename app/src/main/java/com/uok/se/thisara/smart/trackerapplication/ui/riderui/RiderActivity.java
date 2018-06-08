package com.uok.se.thisara.smart.trackerapplication.ui.riderui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.ui.LocationSelectorActivity;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class RiderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rider);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_rider);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_rider);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navigationHeaderView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, null);
        navigationView.addHeaderView(navigationHeaderView);


        ImageView userImage = navigationHeaderView.findViewById(R.id.userImageView);
        TextView userNameText = navigationHeaderView.findViewById(R.id.userNameText);
        TextView emailAddressText = navigationHeaderView.findViewById(R.id.emailAddressText);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Picasso.get()
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .transform(new CropCircleTransformation())
                .fit()
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .into(userImage);

        userNameText.setText(currentUser.getDisplayName());
        emailAddressText.setText(currentUser.getEmail());


        TextView destination = findViewById(R.id.destinationTextView);

        final View contentView = LayoutInflater.from(this).inflate(R.layout.activity_add_new_bus, null);


        destination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent goToGooglePlaceActivity = new Intent(contentView.getContext(), LocationSelectorActivity.class);
                startActivity(goToGooglePlaceActivity);
            }
        });

        Window window = this.getWindow();

        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
