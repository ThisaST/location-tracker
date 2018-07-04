package com.uok.se.thisara.smart.trackerapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.ui.driverui.BusIdentificationActivity;
import com.uok.se.thisara.smart.trackerapplication.ui.riderui.RiderActivity;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;

public class SelectUserTypeActivity extends AppCompatActivity {

    RadioButton driverSelectionButton;
    RadioButton passengerSelectionButton;
    TextView selectUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_type);

        driverSelectionButton = findViewById(R.id.driverRadioButton);
        passengerSelectionButton = findViewById(R.id.passengerRadioButton);
        selectUserType = findViewById(R.id.userSelectionText);

        selectUserType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (driverSelectionButton.isChecked()) {

                    Intent driverViewIntent = new Intent(SelectUserTypeActivity.this, SignInActivity.class);
                    driverViewIntent.putExtra("driverOrPassenger", "Driver");
                    startActivity(driverViewIntent);
                }else if (passengerSelectionButton.isChecked()) {

                    Intent riderViewIntent = new Intent(SelectUserTypeActivity.this, SignInActivity.class);
                    riderViewIntent.putExtra("driverOrPassenger", "Rider");
                    startActivity(riderViewIntent);
                }
            }
        });


        Window window = this.getWindow();

        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);

    }
}
