package com.uok.se.thisara.smart.trackerapplication.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.ui.driverui.MainActivity;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;
import com.uok.se.thisara.smart.trackerapplication.util.FirebaseReferenceConfig;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        /*ImageView profilePicture = findViewById(R.id.profilePicImageView);
        TextView emailText = findViewById(R.id.emailTextView);
        TextView nameText = findViewById(R.id.nameTextView);
        TextView genderText = findViewById(R.id.genderTextView);
        TextView birthdateText = findViewById(R.id.birthdayTextView);

        emailText.setText(FirebaseReferenceConfig.getFirebaseUser().getEmail());
        nameText.setText(FirebaseReferenceConfig.getFirebaseUser().getDisplayName());


        Picasso.get()
                .load(FirebaseReferenceConfig.getFirebaseUser().getPhotoUrl())
                .transform(new CropCircleTransformation())
                .fit()
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .into(profilePicture);*/


        AppCompatImageView profilePicture = findViewById(R.id.profileImage);
        //ImageView profilePicture = findViewById(R.id.profileImage);
        TextView emailText = findViewById(R.id.emailText);
        TextView nameText = findViewById(R.id.nameTextView);
        TextView name = findViewById(R.id.nameText);
        ImageView homeButton = findViewById(R.id.homeButton);

        emailText.setText(FirebaseReferenceConfig.getFirebaseUser().getEmail());
        nameText.setText(FirebaseReferenceConfig.getFirebaseUser().getDisplayName());
        name.setText(FirebaseReferenceConfig.getFirebaseUser().getDisplayName());

        //profilePicture.setImageURI(FirebaseReferenceConfig.getFirebaseUser().getPhotoUrl());

        Picasso.get()
                .load(FirebaseReferenceConfig.getFirebaseUser().getPhotoUrl())
                .transform(new CropCircleTransformation())
                .fit()
                .centerInside()
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .into(profilePicture);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(UserProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        Window window = this.getWindow();

        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);
    }
}
