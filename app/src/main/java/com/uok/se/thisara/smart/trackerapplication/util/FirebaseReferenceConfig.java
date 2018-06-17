package com.uok.se.thisara.smart.trackerapplication.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseReferenceConfig {

    public static DatabaseReference getFirebaseReference(String pathValue) {

        return FirebaseDatabase.getInstance().getReference(pathValue);
    }

    public static FirebaseUser getFirebaseUser() {

        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
