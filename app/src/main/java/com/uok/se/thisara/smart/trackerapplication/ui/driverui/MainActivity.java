package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.glomadrian.materialanimatedswitch.MaterialAnimatedSwitch;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;
import com.uok.se.thisara.smart.trackerapplication.model.BusList;
import com.uok.se.thisara.smart.trackerapplication.service.TrackerService;
import com.uok.se.thisara.smart.trackerapplication.ui.SelectUserTypeActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.SignInActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.SplashScreenActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.TrackerActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.UserProfileActivity;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;
import com.uok.se.thisara.smart.trackerapplication.util.FirebaseReferenceConfig;
import com.uok.se.thisara.smart.trackerapplication.util.GoogleMapConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import dmax.dialog.SpotsDialog;
import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private FirebaseUser currentUser;
    private SupportMapFragment mapFragment;
    private GoogleMapConfig googleMapConfig;

    private MaterialAnimatedSwitch locationSwitch;
    private SpotsDialog waitingDialog;
    private List<Bus> busList;
    private Spinner busListSpinner;
    private String busRegistration;
    private Spinner busRouteSpinner;
    private Spinner busDirectionSpinner;
    private String busRoute;
    private String busDirection;

    public int seconds = 60;
    public int minutes = 10;

    private static final int PERMISSIONS_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        waitingDialog = new SpotsDialog(MainActivity.this);
        waitingDialog.show();
        initializeData();

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
        Button locationTrackButton = findViewById(R.id.startTracking);
        Button stopLocationTracking = findViewById(R.id.stopTracking);
        busListSpinner = findViewById(R.id.busListSpinner);
        busRouteSpinner = findViewById(R.id.busRouteSpinner);
        busDirectionSpinner = findViewById(R.id.busDirection);

        busDirectionSpinner.setEnabled(false);
        List<String> busRouteList = new ArrayList<>();
        busRouteList.add("138");
        busRouteList.add("154");
        busRouteList.add("200");
        setDataToSpinner(busRouteSpinner, busRouteList);

        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        Picasso.get()
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .transform(new CropCircleTransformation())
                .fit()
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .into(userImage);

        userNameText.setText(currentUser.getDisplayName());
        emailAddressText.setText(currentUser.getEmail());

        busRouteSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                List<String> busDirectionData = new ArrayList<>();
                if (adapterView.getSelectedItem().equals("138")) {
                    busDirectionSpinner.setEnabled(true);
                    busDirectionData.add("Kadawatha - Pettah");
                    busDirectionData.add("Pettah - Kadawatha");
                } else if (adapterView.getSelectedItem().equals("154")) {
                    busDirectionSpinner.setEnabled(true);
                    busDirectionData.add("Kiribathgoda - Angulana");
                    busDirectionData.add("Angulana - Kiribathgoda");
                } else if (adapterView.getSelectedItem().equals("200")) {
                    busDirectionSpinner.setEnabled(true);
                    busDirectionData.add("Gampaha - Pettah");
                    busDirectionData.add("Pettah - Gampaha");
                }
                setDataToSpinner(busDirectionSpinner, busDirectionData);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        /**
         * Change the status bar color*/

        Window window = this.getWindow();
        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);

        /*locationSwitch = findViewById(R.id.locationSwitch);
        locationSwitch.setOnCheckedChangeListener(new MaterialAnimatedSwitch.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(boolean isOnline) {

                if (isOnline) {

                    *//*googleMapConfig.buildGoogleApiClient();
                    googleMapConfig.createLocationRequest();
                    googleMapConfig.startLocationUpdates();
                    googleMapConfig.displayLocation();*//*
                    Intent intent = new Intent(MainActivity.this, TrackerActivity.class);
                    Snackbar.make(mapFragment.getView(), "You are Online", Snackbar.LENGTH_SHORT).show();
                    startActivity(intent);

                    Intent newIntent = new Intent(MainActivity.this, DriverLocationActivity.class);
                    startActivity(newIntent);

                }else {

                    *//*googleMapConfig.stopLocationUpdates();*//*
                    Snackbar.make(mapFragment.getView(), "You are Offline", Snackbar.LENGTH_SHORT).show();
                }
            }
        });*/


        locationTrackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
                if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Toast.makeText(MainActivity.this, "Please enable location services", Toast.LENGTH_SHORT).show();
                    finish();
                }

                // Check location permission is granted - if it is, start
                // the service, otherwise request the permission
                int permission = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION);
                if (permission == PackageManager.PERMISSION_GRANTED) {
                    startTrackerService();
                } else {
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSIONS_REQUEST);
                }
            }
        });

        stopLocationTracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopTrackingService();
            }
        });

        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UserProfileActivity.class);
                startActivity(intent);
            }
        });

    }

    private void stopTrackingService() {
        stopService(new Intent(this, TrackerService.class));
    }


    private void initializeData() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Log.d("firebase db reference", FirebaseAuth.getInstance().getCurrentUser().getUid());
        String path = "bus/"  + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/";
        reference.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setBusList(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void setBusList(DataSnapshot dataSnapshot) {

        busList = new ArrayList<>();
        for (DataSnapshot data : dataSnapshot.getChildren()) {

            Bus addedBusses = data.getValue(Bus.class);
            busList.add(addedBusses);
        }

        List<String> busRegistrationNumbers = new ArrayList<>();
        for (Bus bus : busList) {

            busRegistrationNumbers.add(bus.getRegistrationNo());
        }
        setDataToSpinner(busListSpinner, busRegistrationNumbers);
    }

    private void setDataToSpinner(Spinner busListSpinner, List<String> busRegistrationNumbers) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,  busRegistrationNumbers);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
        busListSpinner.setAdapter(adapter);
        waitingDialog.dismiss();
    }

    private void startTrackerService() {

        busRegistration = busListSpinner.getSelectedItem().toString();
        busRoute = busRouteSpinner.getSelectedItem().toString();

        busDirection = busDirectionSpinner.getSelectedItem().toString();
        Intent intent = new Intent(this, TrackerService.class);
        intent.putExtra("bus", busRegistration);
        intent.putExtra("route", busRoute);
        intent.putExtra("direction", busDirection);
        startService(intent);

    }

    public void showTimerInView() {

        final TextView tv = findViewById(R.id.timerView);
        Timer t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {

            @Override
            public void run() {
                runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        tv.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                        seconds -= 1;

                        if(seconds == 0)
                        {
                            tv.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));

                            seconds=60;
                            minutes=minutes-1;

                        }
                    }

                });
            }

        }, 0, 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (requestCode == PERMISSIONS_REQUEST && grantResults.length == 1
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            // Start the service when the permission is granted
            startTrackerService();
        } else {
            finish();
        }

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

        /*mapFragment = (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.mapDriver);
        mapFragment.getMapAsync(googleMapConfig);*/
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
            Intent intent = new Intent(this, SignInActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void dataValidation() {

        if (busRegistration == null) {
            createToast("Please Select a bus from the dropdown");
        }
    }

    public void createToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
