package com.uok.se.thisara.smart.trackerapplication.ui.riderui;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
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
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.ui.SelectUserTypeActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.UserProfileActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.driverui.BusHistoryActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.driverui.BusRouteActivity;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;
import com.uok.se.thisara.smart.trackerapplication.util.GoogleMapConfig;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class RiderActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener {

    private FirebaseUser currentUser;
    private SupportMapFragment mapFragment;
    private GoogleMapConfig googleMapConfig;
    private GoogleMap mMap;

    private static final int PERMISSION_REQUEST_CODE = 7000;
    private static final int PLAY_SERVICE_RES_REQUEST = 7001;

    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;

    private static int UPDATE_INTERVAL = 2000;
    private static int FASTEST_INTERVAL = 1000;
    private static int DISPLACEMENT = 10;

    DatabaseReference riders;
    GeoFire geoFire;
    Marker mCurrent;

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

        createMapObject();


        //implement the google map
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMapConfig);
    }

    private void createMapObject() {

        googleMapConfig = new GoogleMapConfig(this);
        assignTheMap(googleMapConfig);
    }

    private void assignTheMap(GoogleMapConfig googleMapConfig) {

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(googleMapConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_bus_route) {

            Intent intent = new Intent(this, BusRouteActivity.class);
            startActivity(intent);

            // Handle the camera action
        } else if (id == R.id.nav_history) {

            Intent intent = new Intent(this, BusHistoryActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_summary) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_signout) {

            FirebaseAuth.getInstance().signOut();
            Toast.makeText(RiderActivity.this, "signed out", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, SelectUserTypeActivity.class);
            startActivity(intent);
        }else if (id == R.id.userProfile) {

            Intent profileIntent = new Intent(this, UserProfileActivity.class);
            startActivity(profileIntent);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout_rider);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
