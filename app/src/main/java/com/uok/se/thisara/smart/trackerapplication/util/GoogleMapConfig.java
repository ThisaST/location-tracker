package com.uok.se.thisara.smart.trackerapplication.util;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.uok.se.thisara.smart.trackerapplication.R;



public class GoogleMapConfig implements OnMapReadyCallback {

    private static final String TAG = GoogleMapConfig.class.getSimpleName();
    private Context mapContext;

    public GoogleMapConfig(Context context) {
        mapContext = context;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            mapContext, R.raw.uber_map_style));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

    }

}
