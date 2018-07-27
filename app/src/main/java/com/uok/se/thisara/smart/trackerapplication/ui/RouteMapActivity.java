package com.uok.se.thisara.smart.trackerapplication.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.uok.se.thisara.smart.trackerapplication.R;

import java.util.ArrayList;
import java.util.List;

public class RouteMapActivity extends AppCompatActivity implements
        OnMapReadyCallback,
        GoogleMap.OnPolylineClickListener,
        GoogleMap.OnPolygonClickListener {

    private static final String TAG = RouteMapActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.busRouteMap);
        mapFragment.getMapAsync(this);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

    }

    @Override
    public void onPolygonClick(Polygon polygon) {

    }

    @Override
    public void onPolylineClick(Polyline polyline) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        /*Polyline polyline1 = googleMap.addPolyline(new PolylineOptions()
                .clickable(true)
                .add(
                        new LatLng(-35.016, 143.321),
                        new LatLng(-34.747, 145.592),
                        new LatLng(-34.364, 147.891),
                        new LatLng(-33.501, 150.217),
                        new LatLng(-32.306, 149.248),
                        new LatLng(-32.491, 147.309)));*/

        // Position the map's camera near Alice Springs in the center of Australia,
        // and set the zoom factor so most of Australia shows on the screen.


        // Set listeners for click events.
        googleMap.setOnPolylineClickListener(this);
        googleMap.setOnPolygonClickListener(this);
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            RouteMapActivity.this, R.raw.uber_map_style));
        }catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        List<LatLng> routeLocations = new ArrayList<>();

        routeLocations.add(new LatLng(7.0014245, 79.9498839));
        routeLocations.add(new LatLng(7.002349, 79.952664));
        routeLocations.add(new LatLng(7.002136, 79.952052));
        routeLocations.add(new LatLng(7.001806, 79.951172));
        routeLocations.add(new LatLng(7.001337, 79.950528));
        routeLocations.add(new LatLng(7.001092, 79.950152));
        routeLocations.add(new LatLng(7.000900, 79.949830));
        routeLocations.add(new LatLng(7.000644, 79.949186));
        routeLocations.add(new LatLng(7.000356, 79.948703));
        routeLocations.add(new LatLng(7.000037, 79.948446));
        routeLocations.add(new LatLng(6.999781, 79.948296));
        routeLocations.add(new LatLng(6.999356, 79.948168));
        routeLocations.add(new LatLng(6.998344, 79.948297));
        routeLocations.add(new LatLng(6.997662, 79.948254));
        routeLocations.add(new LatLng(6.997215, 79.948168));
        routeLocations.add(new LatLng(6.996651, 79.947964));
        routeLocations.add(new LatLng(6.996279, 79.947660));
        routeLocations.add(new LatLng(6.995383, 79.946737));
        routeLocations.add(new LatLng(6.994655, 79.945376));
        routeLocations.add(new LatLng(6.993961, 79.944366));
        routeLocations.add(new LatLng(6.992571, 79.942810));
        routeLocations.add(new LatLng(6.992241, 79.942086));
        routeLocations.add(new LatLng(6.991570, 79.941512));
        routeLocations.add(new LatLng(6.991229, 79.940992));
        routeLocations.add(new LatLng(6.991005, 79.940139));
        routeLocations.add(new LatLng(6.991000, 79.939039));
        routeLocations.add(new LatLng(6.990792, 79.938717));
        routeLocations.add(new LatLng(6.989605, 79.938202));
        routeLocations.add(new LatLng(6.987795, 79.937188));
        routeLocations.add(new LatLng(6.986996, 79.936426));
        routeLocations.add(new LatLng(6.986788, 79.936077));
        routeLocations.add(new LatLng(6.986570, 79.935390));
        routeLocations.add(new LatLng(6.985787, 79.934687));
        routeLocations.add(new LatLng(6.984190, 79.933137));
        routeLocations.add(new LatLng(6.982641, 79.931495));
        routeLocations.add(new LatLng(6.980564, 79.929634));
        routeLocations.add(new LatLng(6.977486, 79.926620));
        routeLocations.add(new LatLng(6.976362, 79.925477));
        routeLocations.add(new LatLng(6.975537, 79.924721));
        routeLocations.add(new LatLng(6.974307, 79.922575));
        routeLocations.add(new LatLng(6.973322, 79.919963));
        routeLocations.add(new LatLng(6.972843, 79.917994));
        routeLocations.add(new LatLng(6.972545, 79.917549));
        routeLocations.add(new LatLng(6.971810, 79.916798));
        routeLocations.add(new LatLng(6.970410, 79.914888));
        routeLocations.add(new LatLng(6.969606, 79.913343));
        /*routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());
        routeLocations.add(new LatLng());*/

        /*routeLocations.add(new LatLng(6.971819, 79.916800));
        routeLocations.add(new LatLng(7.0014245, 79.9498839));*/
        PolylineOptions options = new PolylineOptions().width(5).color(Color.BLUE).geodesic(true);
        for (int z = 0; z < routeLocations.size(); z++) {
            LatLng point = routeLocations.get(z);
            options.add(point);
        }

        Polyline busRoute = googleMap.addPolyline(options);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(7.0014245, 79.9498839), 4));



        /*for(int i = 0 ; i < markersArray.size() ; i++) {

            createMarker(markersArray.get(i).getLatitude(), markersArray.get(i).getLongitude(), markersArray.get(i).getTitle(), markersArray.get(i).getSnippet(), markersArray.get(i).getIconResID());
        }*/
    }




    protected Marker createMarker(GoogleMap googleMap, double latitude, double longitude, String title, String snippet, int iconResID) {

        return googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .anchor(0.5f, 0.5f)
                .title(title)
                .snippet(snippet)
                .icon(BitmapDescriptorFactory.fromResource(iconResID)));
    }
}
