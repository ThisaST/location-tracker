package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.ui.SelectUserTypeActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.SignInActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.SplashScreenActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.TrackerActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.UserProfileActivity;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;
import com.uok.se.thisara.smart.trackerapplication.util.GoogleMapConfig;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseUser currentUser;
    private SupportMapFragment mapFragment;
    private GoogleMapConfig googleMapConfig;

    private MaterialAnimatedSwitch locationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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

        createMapObject();
        /**
         * Change the status bar color*/

        Window window = this.getWindow();
        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);

        locationSwitch = findViewById(R.id.locationSwitch);
        locationSwitch.setOnCheckedChangeListener(new MaterialAnimatedSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isOnline) {

                if (isOnline) {

                    /*googleMapConfig.buildGoogleApiClient();
                    googleMapConfig.createLocationRequest();
                    googleMapConfig.startLocationUpdates();
                    googleMapConfig.displayLocation();*/
                    Intent intent = new Intent(MainActivity.this, TrackerActivity.class);
                    Snackbar.make(mapFragment.getView(), "You are Online", Snackbar.LENGTH_SHORT).show();
                    startActivity(intent);

                    Intent newIntent = new Intent(MainActivity.this, DriverLocationActivity.class);
                    startActivity(newIntent);

                }else {

                    /*googleMapConfig.stopLocationUpdates();*/
                    Snackbar.make(mapFragment.getView(), "You are Offline", Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        //style the map


        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        googleMapConfig.onPermissionResult(requestCode, grantResults);

        if (locationSwitch.isChecked()) {

            googleMapConfig.buildGoogleApiClient();
            googleMapConfig.createLocationRequest();
            googleMapConfig.displayLocation();
        }
    }

    private void createMapObject() {

        googleMapConfig = new GoogleMapConfig(this);
        assignTheMap(googleMapConfig);
    }

    private void assignTheMap(GoogleMapConfig googleMapConfig) {

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapDriver);
        mapFragment.getMapAsync(googleMapConfig);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_bus_route) {

            Intent intent = new Intent(this, BusRouteActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_bus) {

            Intent intent = new Intent(this, BusIdentificationActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_history) {

            Intent intent = new Intent(this, BusHistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_summary) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_signout) {

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(MainActivity.this, "signed out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SelectUserTypeActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
