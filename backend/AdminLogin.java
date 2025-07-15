package com.example.hotelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AdminLogin extends AppCompatActivity implements View.OnClickListener {

    private TextView login,userLogin,register;
    private EditText email,password;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

        login=(TextView) findViewById(R.id.login);
        login.setOnClickListener(this);

        email=(EditText) findViewById(R.id.email);
        email.setOnClickListener(this);

        password=(EditText) findViewById(R.id.password);
        password.setOnClickListener(this);

        register= (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        userLogin= (TextView) findViewById(R.id.userLogin);
        userLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.userLogin:
                startActivity(new Intent(this,MainActivity.class));
                break;

            case R.id.register:
                startActivity(new Intent(this,Register.class));
                break;

            case R.id.login:
                LoginAdmin();
                break;

        }
    }

    public void LoginAdmin() {
        String email_valid = email.getText().toString().trim();
        String password_valid = password.getText().toString().trim();

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        if (email_valid.isEmpty()) {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email_valid).matches()) {
            email.setError("Email is invalid");
            email.requestFocus();
            return;
        }
        if (password_valid.isEmpty()) {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if (password_valid.length() < 6) {
            password.setError("Password should be minimum of 6 characters");
            password.requestFocus();
            return;
        }


        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserDataHotel Profile = snapshot.getValue(UserDataHotel.class);
                if (Profile != null) {
                    Toast.makeText(AdminLogin.this, Profile.role, Toast.LENGTH_SHORT).show();
                    sendInfo(Profile.role);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminLogin.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void sendInfo(String s) {
        if(s.equals("Admin"))
            startActivity(new Intent(this,AdminHome.class));
       // else
            //Toast.makeText(AdminLogin.this, "Error", Toast.LENGTH_SHORT).show();


    }
}