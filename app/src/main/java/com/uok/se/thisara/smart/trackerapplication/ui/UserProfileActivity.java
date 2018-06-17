package com.uok.se.thisara.smart.trackerapplication.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.util.FirebaseReferenceConfig;

import jp.wasabeef.picasso.transformations.CropCircleTransformation;

public class UserProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        ImageView profilePicture = findViewById(R.id.profilePicImageView);
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
                .into(profilePicture);



    }
}
