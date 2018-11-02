package com.uok.se.thisara.smart.trackerapplication.ui.driverui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.uok.se.thisara.smart.trackerapplication.R;
import com.uok.se.thisara.smart.trackerapplication.model.Bus;
import com.uok.se.thisara.smart.trackerapplication.model.Upload;
import com.uok.se.thisara.smart.trackerapplication.util.Configuration;
import com.uok.se.thisara.smart.trackerapplication.util.FirebaseReferenceConfig;
import com.uok.se.thisara.smart.trackerapplication.util.RunTimePermission;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddNewBusActivity extends AppCompatActivity {

    private Button addNewBusButton;
    private EditText vehicleNumber;
    private EditText ownerName;
    private EditText contactNumber;
    private Spinner vehicleModel;
    private ImageButton vehicleImage;
    private int GALLERY = 1;
    private int CAMERA = 0;
    private String userChoosenTask;
    private FirebaseDatabase mDatabase;
    private StorageReference firebaseStorageReference;
    private StorageTask uploadingImageTask;
    private Uri mImageUri;
    private ProgressBar mProgressBar;
    private List<Bus> busList;
    private DataSnapshot availableBusses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_bus);

        //get the references of the widgets
        addNewBusButton = findViewById(R.id.addNewBusButton);
        vehicleNumber = findViewById(R.id.vehicleRegisterNo);
        ownerName = findViewById(R.id.ownerName);
        contactNumber = findViewById(R.id.contactNo);
        vehicleImage = findViewById(R.id.vehicleImageButton);
        vehicleModel = findViewById(R.id.busModelSpinner);
        mProgressBar = findViewById(R.id.imageUploadProgressBar);
        Log.d("test", vehicleNumber.getText().toString());
        //add values to the spinner
        addDataToSpinner(vehicleModel);

        //image button implementation to get the image from the Gallery or from the camera
        vehicleImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        FirebaseReferenceConfig firebaseReferenceConfig = new FirebaseReferenceConfig();
        try {
            availableBusses = firebaseReferenceConfig.getFirebaseData("bus/" + FirebaseAuth.getInstance().getCurrentUser().getUid());
        } catch (NullPointerException ex) {

            Toast.makeText(this, "No added vahicles", Toast.LENGTH_LONG).show();
        }

        busList = new ArrayList<>();

        //store the image in firebase
        firebaseStorageReference = FirebaseStorage.getInstance().getReference("uploads");
        mDatabase = FirebaseDatabase.getInstance();
        addNewBusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                storeDataInFirebase();
                goToBusIdentificationActivity();
            }
        });


        Window window = this.getWindow();

        Configuration.changeStatusBarColor(window, this, R.color.colorPrimaryDark);
    }

    private void goToBusIdentificationActivity() {

        Intent intent = new Intent(this, BusIdentificationActivity.class);
        startActivity(intent);
    }

    private void storeDataInFirebase() {

        try {
            for (DataSnapshot data : availableBusses.getChildren()) {

                Bus addedBus = data.getValue(Bus.class);
                busList.add(addedBus);
            }
        }catch (NullPointerException e) {

        }
        int newBusID;
        if (availableBusses == null) {
            newBusID = 0;
        }else {
            newBusID = generateIdForBus(busList.size());
        }


        String dbPath = "bus/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/" + Integer.toString(newBusID);
        DatabaseReference dbReference = mDatabase.getReference(dbPath);


        Bus newBus = new Bus(vehicleNumber.getText().toString(), ownerName.getText().toString(), newBusID, vehicleModel.getSelectedItem().toString());
        dbReference.setValue(newBus);
    }

    private void addDataToSpinner(Spinner s) {

        String[] arraySpinner = new String[] {
                "TATA", "LEYLAND", "EICHER", "MICRO", "ISUZU", "MITSUBISHI"
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
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
        builder.setTitle("Add Image");
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

        mImageUri = data.getData();
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

        uploadImageToFirebase();
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
        uploadImageToFirebase();
    }

    public int generateIdForBus(int size) {
        //bus list size
        //add one to the variable to create the new bus id
        return size++;
    }

    public void uploadImageToFirebase() {
        if (mImageUri != null) {
            StorageReference fileReference = firebaseStorageReference.child(vehicleNumber.getText()
                    + "." + getFileExtension(mImageUri));

            uploadingImageTask = fileReference.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    mProgressBar.setProgress(0);
                                }
                            }, 500);

                            Toast.makeText(AddNewBusActivity.this, "Upload successful", Toast.LENGTH_LONG).show();
                            Upload upload = new Upload(vehicleNumber.getText().toString().trim(),
                                    taskSnapshot.getDownloadUrl().toString());
                            String uploadId = mDatabase.getReference().push().getKey();
                            mDatabase.getReference().child(uploadId).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AddNewBusActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            mProgressBar.setProgress((int) progress);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }
}
