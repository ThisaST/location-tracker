package com.uok.se.thisara.smart.trackerapplication.ui;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.uok.se.thisara.smart.trackerapplication.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent signInActivityIntent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                startActivity(signInActivityIntent);
                finish();
            }
        }, 3000);

    }
}
