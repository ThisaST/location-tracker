package com.uok.se.thisara.smart.trackerapplication.remote;

import android.location.Location;


import com.uok.se.thisara.smart.trackerapplication.model.BusLocation;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by malware on 4/8/18.
 */

public interface LocationClient {

    @POST("/api/location")
    Call<BusLocation> sendCurrentBusLocation(@Body BusLocation busLocation);
}
