package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.util.RunTimePermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddNewBusActivity extends AppCompatActivity {

    private Button addNewBusButton;
    private EditText vehicleNumber;
    private EditText ownerName;
    private Spinner vehicleModel;
    private ImageButton vehicleImage;
    private int GALLERY = 1;
    private int CAMERA = 0;
    private String userChoosenTask;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_bus);


        //get the references of the widgets
        addNewBusButton = findViewById(R.id.addNewBusButton);
        vehicleNumber = findViewById(R.id.vehicleRegisterNo);
        ownerName = findViewById(R.id.ownerName);
        vehicleImage = findViewById(R.id.vehicleImageButton);


        //add values to the spinner
        addDataToSpinner();

        //image button implementation to get the image from the Gallery or from the camera
        vehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        //add data to Firebase database

        mDatabase = FirebaseDatabase.getInstance();
        DatabaseReference dbReference = mDatabase.getReference();





        //store the image inside the device storage



    }

    private void addDataToSpinner() {

        String[] arraySpinner = new String[] {
                "TATA", "LEYLAND", "EICHER", "MICRO", "ISUZU", "MITSUBISHI"
        };
        Spinner s = (Spinner) findViewById(R.id.busModelSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spin‌​ner_dropdown_item);
        s.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case RunTimePermission.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(userChoosenTask.equals("Camera"))
                        cameraIntent();
                    else if(userChoosenTask.equals("Gallery"))
                        galleryIntent();
                } else {
                    //code for deny
                }
                break;
        }
    }


    private void selectImage() {
        final CharSequence[] items = {  "Camera", "Gallery",
                "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddNewBusActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result=RunTimePermission.checkPermission(AddNewBusActivity.this);

                if (items[item].equals("Camera")) {
                    userChoosenTask ="Camera";
                    if(result)
                        cameraIntent();

                } else if (items[item].equals("Gallery")) {
                    userChoosenTask ="Gallery";
                    if(result)
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void galleryIntent()
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"),GALLERY);
    }

    private void cameraIntent()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == GALLERY)
                onSelectFromGalleryResult(data);
            else if (requestCode == CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        vehicleImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        vehicleImage.setImageBitmap(bm);
    }
}
