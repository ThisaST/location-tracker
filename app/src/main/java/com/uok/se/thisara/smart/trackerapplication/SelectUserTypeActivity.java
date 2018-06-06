package com.uok.se.thisara.smart.trackerapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SelectUserTypeActivity extends AppCompatActivity {

    private Button riderActivityLogin;
    private Button driverActivityLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);

        riderActivityLogin = findViewById(R.id.busRiderLoginButton);
        driverActivityLogin = findViewById(R.id.busDriverLoginButton);

        riderActivityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent riderViewIntent = new Intent(SelectUserTypeActivity.this, RiderActivity.class);
                startActivity(riderViewIntent);
            }
        });

        driverActivityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent driverViewIntent = new Intent(SelectUserTypeActivity.this, BusIdentificationActivity.class);
                startActivity(driverViewIntent);
            }
        });

    }
}
