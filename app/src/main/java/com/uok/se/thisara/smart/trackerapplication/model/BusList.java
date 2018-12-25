package com.uok.se.thisara.smart.trackerapplication.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.uok.se.thisara.smart.trackerapplication.util.FirebaseReferenceConfig;

import java.util.ArrayList;
import java.util.List;

public class BusList {

    private static List<Bus> busList;

    public void setBusList(List<Bus> busList) {
        this.busList = busList;
    }

    public static List<Bus> getBusList() {
        List<Bus> newListOfBusses = new ArrayList<>();
        if (busList != null) {
            newListOfBusses = busList;
        }else {

            String path = "bus/"  + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/";
            DataSnapshot dataSnapshot = new FirebaseReferenceConfig().getDataSnapshot(path);

            for (DataSnapshot data: dataSnapshot.getChildren()) {

                Bus addedBusses = data.getValue(Bus.class);
                busList.add(addedBusses);
            }

            newListOfBusses = busList;
        }
        return newListOfBusses;
    }
}
