package com.uok.se.thisara.smart.trackerapplication.util;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Window;
import android.view.WindowManager;

import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.remote.IGoogleAPI;
import com.uok.se.thisara.smart.trackerapplication.remote.LocationClient;
import com.uok.se.thisara.smart.trackerapplication.remote.RetrofitClient;

/**
 * Created by malware on 4/11/18.
 */

public class Configuration {

    public static final String baseUrl = "http://192.168.8.100:8080";
    public static final String baseURL = "https://maps.googleapis.com";


    public static IGoogleAPI getGoogleApi() {

        return RetrofitClient.getRetrofitClient(baseURL).create(IGoogleAPI.class);
    }

    public static LocationClient sendLocation() {

        return RetrofitClient.getRetrofitClient(baseUrl).create(LocationClient.class);
    }

    public static void changeStatusBarColor(Window uiWindow, Context context, int colorId) {

        uiWindow.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        uiWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

        // finally change the color
        uiWindow.setStatusBarColor(ContextCompat.getColor(context, colorId));
    }
}
