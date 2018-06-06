package com.uok.se.thisara.smart.trackerapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.uok.se.thisara.smart.trackerapplication.model.BusLocation;

public class LocationTrackingViewModel extends ViewModel {

    private LiveData<BusLocation> busLocationLiveData;

    public LocationTrackingViewModel() {

        busLocationLiveData = new MutableLiveData<>();
    }

    public LiveData<BusLocation> getLiveLocation() {

        return busLocationLiveData;
    }
}
