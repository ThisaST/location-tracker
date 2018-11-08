package com.uok.se.thisara.smart.trackerapplication.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;
import com.uok.se.thisara.smart.trackerapplication.util.FirebaseReferenceConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Singleton;

@Singleton
public class BusListRepository {

    public LiveData<List<Bus>> getBusList() {

        MutableLiveData<List<Bus>> liveDataList = new MutableLiveData<>();
        String dbPath = "bus/" + FirebaseReferenceConfig.getFirebaseUser().getUid();

        //DataSnapshot dataSnapshot = FirebaseReferenceConfig.getFirebaseData(dbPath);

        FirebaseReferenceConfig firebaseReferenceConfig = new FirebaseReferenceConfig();
        firebaseReferenceConfig.getFirebaseData(dbPath);
        DataSnapshot dataSnapshot = firebaseReferenceConfig.getDataSnapshot();

        HashMap<String, Object> busValues = (HashMap<String, Object>) dataSnapshot.getValue();
        List<Bus> busList;
        Bus newBus = new Bus();
        newBus.setBusModel(busValues.get("busModel").toString());
        newBus.setRegistrationNo(busValues.get("registrationNo").toString());
        newBus.setOwnerName(busValues.get("ownerName").toString());
        //newBus.setImageId(Integer.parseInt(busValues.get("imageId").toString()));



        busList = new ArrayList<>();
        busList.add(newBus);
        busList.add(new Bus("wpXA-1234", "Nilupul Shenal", "", "LEYLAND"));
        int size= busList.size();
        Log.d("Bus List Size", Integer.toString(size));

        liveDataList.postValue(busList);

        return liveDataList;
    }

}
