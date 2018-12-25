package com.uok.se.thisara.smart.trackerapplication.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseReferenceConfig {

    private DataSnapshot dataSnapshotN;

    public static DatabaseReference getFirebaseReference() {

        return FirebaseDatabase.getInstance().getReference();
    }

    public static FirebaseUser getFirebaseUser() {

        return FirebaseAuth.getInstance().getCurrentUser();
    }

    public DataSnapshot getFirebaseData(String dbReferencePath) {

        DatabaseReference firebaseDatabaseReference = FirebaseDatabase.getInstance().getReference(dbReferencePath);
        firebaseDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                dataSnapshotN = dataSnapshot;
                setDataSnapshotValue(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return dataSnapshotN;
    }

    public void setDataSnapshotValue(DataSnapshot dataSnapshot) {

        this.dataSnapshotN = dataSnapshot;
    }

    public DataSnapshot getDataSnapshot(String path) {

        new FirebaseReferenceConfig().getFirebaseData(path);
        return this.dataSnapshotN;
    }


}
