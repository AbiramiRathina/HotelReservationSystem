package com.example.hotelbookingapp;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Home extends AppCompatActivity {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private int i;

    private TextView nameValue, priceValue, locationValue, starsValue;
    private LinearLayout containerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance("https://hotelbookingapp-22a7b-default-rtdb.firebaseio.com/").getReference("Hotels");
        userID = user.getUid();


        containerLayout = (LinearLayout) findViewById(R.id.container);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    HotelData h = snapshot.getValue(HotelData.class);


                    if (h != null) {

                        LayoutInflater layoutInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                        final View addView = layoutInflater.inflate(R.layout.data, null);
                        TextView nametf = (TextView) addView.findViewById(R.id.nameValue);
                        TextView loctf = (TextView) addView.findViewById(R.id.locationValue);
                        TextView pricetf = (TextView) addView.findViewById(R.id.priceValue);
                        TextView starstf = (TextView) addView.findViewById(R.id.starsValue);
                        Button bookButton = (Button) addView.findViewById(R.id.book);


                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        {
                            NotificationChannel channel = new NotificationChannel("My Notification","My Notification", NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager=getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }
                        nametf.setText(h.name);
                        loctf.setText(h.location);
                        pricetf.setText(h.price);
                        starstf.setText(h.stars);

                        bookButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                NotificationCompat.Builder builder = new NotificationCompat.Builder(Home.this,"My Notification");
                                builder.setContentTitle("Booking confirmed!");
                                builder.setContentText("Thanks for booking "+nametf.getText()+"\n\nYour Total bill amount is "+pricetf.getText()+"\n\nEnjoy your stay!");
                                builder.setSmallIcon(R.drawable.ic_launcher_background);
                                builder.setAutoCancel(true);
                                NotificationManagerCompat managerCompat=NotificationManagerCompat.from(Home.this);
                                managerCompat.notify(1,builder.build());

                                //TextView hotel= (TextView) view.findViewById(R.id.nameValue);

//                                Intent email = new Intent(Intent.ACTION_SEND);
//                                email.setData(Uri.parse("mailto:"));
//                                email.setType("text/plain");
//
//                                email.putExtra(Intent.EXTRA_EMAIL, new String[] {user.getEmail()});
//
//                                email.putExtra(Intent.EXTRA_SUBJECT,"Booking confirmed!");
//
//                                email.putExtra(Intent.EXTRA_TEXT,"Thanks for booking "+nametf.getText()+"\n\nYour Toatal bill amount is "+pricetf.getText()+"\n\nEnjoy your stay!");

                                //System.out.println(nametf.getText());
                               // Toast.makeText(Home.this,hotel.toString(),Toast.LENGTH_SHORT).show();
                                // sendEmail();

//                                try{
//                                    startActivity(Intent.createChooser(email,"Choose an email client"));
//                                }
//                                catch (Exception e){
//                                    Toast.makeText(Home.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//                                }
                            }
                        });


                        containerLayout.addView(addView);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

}