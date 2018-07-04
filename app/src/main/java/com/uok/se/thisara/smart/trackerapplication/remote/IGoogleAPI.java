package com.uok.se.thisara.smart.trackerapplication.remote;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by malware on 3/28/18.
 */

public interface IGoogleAPI {

    @GET
    Call<String> getPath(@Url String url);
}
