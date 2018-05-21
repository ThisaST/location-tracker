package com.uok.se.thisara.smart.trackerapplication.util;

import com.uok.se.thisara.smart.trackerapplication.remote.LocationClient;
import com.uok.se.thisara.smart.trackerapplication.remote.RetrofitClient;

/**
 * Created by malware on 4/11/18.
 */

public class Configuration {

    public static final String baseUrl = "http://192.168.8.100:8080";

    public static LocationClient sendLocation() {

        return RetrofitClient.getRetrofitClient(baseUrl).create(LocationClient.class);
    }
}
