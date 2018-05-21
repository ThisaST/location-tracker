package com.uok.se.thisara.smart.trackerapplication.remote;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by malware on 4/11/18.
 */

public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getRetrofitClient(String baseUrl) {

        if (retrofit == null) {

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
