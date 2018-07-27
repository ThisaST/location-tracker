package com.uok.se.thisara.smart.trackerapplication.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseAuth;

public class SignInActivityViewModel extends ViewModel {

    private MutableLiveData<FirebaseAuth> firebaseAuthMutableLiveData;

    public SignInActivityViewModel() {

        firebaseAuthMutableLiveData = new MutableLiveData<>();
    }

    public LiveData<FirebaseAuth> getFirebaseUserDetails() {

        return firebaseAuthMutableLiveData;
    }
}
