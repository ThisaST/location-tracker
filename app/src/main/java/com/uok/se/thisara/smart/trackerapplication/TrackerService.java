package com.uok.se.thisara.smart.trackerapplication;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uok.se.thisara.smart.trackerapplication.model.BusLocation;
import com.uok.se.thisara.smart.trackerapplication.remote.LocationClient;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;

import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Location;
import android.Manifest;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrackerService extends Service {

    private static final String TAG = TrackerService.class.getSimpleName();
    private LocationClient mLocationClient;
    //private BusLocation busLocation = null;

    @Override
    public IBinder onBind(Intent intent) {return null;}

    @Override
    public void onCreate() {
        super.onCreate();
        buildNotification();
        //loginToFirebase();
        requestLocationUpdates();
    }

    private void buildNotification() {
        String stop = "stop";
        registerReceiver(stopReceiver, new IntentFilter(stop));
        PendingIntent broadcastIntent = PendingIntent.getBroadcast(
                this, 0, new Intent(stop), PendingIntent.FLAG_UPDATE_CURRENT);
        // Create the persistent notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notification_text))
                .setOngoing(true)
                .setContentIntent(broadcastIntent)
                .setSmallIcon(R.drawable.ic_tracker);
        startForeground(1, builder.build());
    }

    protected BroadcastReceiver stopReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "received stop broadcast");
            // Stop the service when the notification is tapped
            unregisterReceiver(stopReceiver);
            stopSelf();
        }
    };

    private void loginToFirebase() {
        // Functionality coming next step
        String email = getString(R.string.firebase_email);
        String password = getString(R.string.firebase_password);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "firebase auth success");
                    requestLocationUpdates();
                } else {
                    Log.d(TAG, "firebase auth failed");
                }
            }
        });
    }

    private void requestLocationUpdates() {
        // Functionality coming next step

        LocationRequest request = new LocationRequest();
        request.setInterval(3000);
        request.setFastestInterval(1000);
        request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        FusedLocationProviderClient client = LocationServices.getFusedLocationProviderClient(this);

        mLocationClient = Configuration.sendLocation();
        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        if (permission == PackageManager.PERMISSION_GRANTED) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            client.requestLocationUpdates(request, new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    final String path = getString(R.string.firebase_path) + "/" + getString(R.string.bus_id);
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
                    Location location = locationResult.getLastLocation();
                    if (location != null && location.getAccuracy() != 0){
                        Log.d(TAG, "location update " + location);
                        ref.setValue(location);
                        BusLocation busLocation = new BusLocation();

                        busLocation.setAccuracy(location.getAccuracy());
                        busLocation.setProvider(location.getProvider());
                        busLocation.setAltitude(location.getAltitude());
                        busLocation.setLatitude(location.getLatitude());
                        busLocation.setLongitude(location.getLongitude());
                        busLocation.setCurrentTime(location.getTime());
                        busLocation.setSpeed(location.getSpeed());

                        Log.d(TAG,"bus location object" + busLocation);

                        if (busLocation == null) {

                        }
                        mLocationClient.sendCurrentBusLocation(busLocation)
                                .enqueue(new Callback<BusLocation>() {
                                    @Override
                                    public void onResponse(Call<BusLocation> call, Response<BusLocation> response) {

                                        if (response.isSuccessful()) {

                                            Log.i(TAG, "post submitted to API." + response.body().toString());

                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<BusLocation> call, Throwable t) {

                                        Log.e(TAG, "Unable to submit post to API.");
                                    }
                                });
                    }
                }
            }, null);
        }


    }
}
