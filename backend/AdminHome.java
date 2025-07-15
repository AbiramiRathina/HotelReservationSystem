package com.example.hotelbookingapp;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.concurrent.atomic.AtomicMarkableReference;

public class AdminHome extends AppCompatActivity implements View.OnClickListener{

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private FirebaseAuth mAuth;


    private Button captureImageBtn, addHotel, galleryImageBtn;
    private Bitmap bitmap;

    private String nameValid,loc,pri,s,urlImg;

    private EditText name,location,price,stars;


    private Uri imgUri;
    private FirebaseDatabase database;
    private DatabaseReference ref;

    private StorageReference stref = FirebaseStorage.getInstance().getReference().child("ImageFolder");


    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        database=FirebaseDatabase.getInstance();

        name=findViewById(R.id.name);
        location=findViewById(R.id.location);
        price=findViewById(R.id.price);
        stars=findViewById(R.id.stars);



        addHotel = findViewById(R.id.AddHotel);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://hotelbookingapp-22a7b-default-rtdb.firebaseio.com/").getReference("Hotels");
        userID = user.getUid();


        ref=database.getReference();
        mAuth = FirebaseAuth.getInstance();

        addHotel.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View view) {
                try {
                    nameValid = name.getText().toString().trim();
                    loc = location.getText().toString().trim();
                    pri = price.getText().toString().trim();
                    s = stars.getText().toString().trim();

                    if (nameValid.isEmpty()) {
                        name.setError("Name is required");
                        name.requestFocus();
                        return;
                    }
                    if (loc.isEmpty()) {
                        location.setError("Quantity is required");
                        location.requestFocus();
                        return;
                    }
                    if (pri.isEmpty()) {
                        price.setError("Manufacturing date is required");
                        price.requestFocus();
                        return;
                    }
                    if (s.isEmpty()) {
                        stars.setError("Expiry date is required");
                        stars.requestFocus();
                        return;
                    }

                     sendData();

                }
                catch(Exception e)
                {

                }
            }
        }
        );

    }






    private void sendData() {

        HotelData hotel=new HotelData(nameValid,loc,pri,s);
        Toast.makeText(AdminHome.this,"Hotel Added",Toast.LENGTH_SHORT).show();
        ref.child("Hotels").push().setValue(hotel);

    }


    @Override
    public void onClick(View v) {


    }
}