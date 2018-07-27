package com.uok.se.thisara.smart.trackerapplication.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseReferenceConfig {

    private DataSnapshot dataSnapshot;

    public static DatabaseReference getFirebaseReference() {

        return FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseUser getFirebaseUser() {

        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public void getFirebaseData(String dbReferencePath) {


        FirebaseReferenceConfig.getFirebaseReference().child(dbReferencePath).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                setDataSnapshotValue(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void setDataSnapshotValue(DataSnapshot dataSnapshot) {

        this.dataSnapshot = dataSnapshot;
    }

    public DataSnapshot getDataSnapshot() {

        return this.dataSnapshot;
    }


}
