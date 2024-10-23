package com.example.hotelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.service.autofill.UserData;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Register extends AppCompatActivity implements View.OnClickListener{


    private TextView register,login;
    private EditText name,age,email,password,verifypassword,role;


    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login=(TextView) findViewById(R.id.login);
        login.setOnClickListener(this);

        name=(EditText) findViewById(R.id.name);
        name.setOnClickListener(this);

        age=(EditText) findViewById(R.id.age);
        age.setOnClickListener(this);

        email=(EditText) findViewById(R.id.email);
        email.setOnClickListener(this);

        password=(EditText) findViewById(R.id.password);
        password.setOnClickListener(this);

        verifypassword=(EditText) findViewById(R.id.verifypassword);
        verifypassword.setOnClickListener(this);

        role = (EditText) findViewById(R.id.role);
        role.setOnClickListener(this);

    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.register:
                RegisterUser();
                break;


            case R.id.login:
                startActivity(new Intent(this,MainActivity.class));
                break;

        }
    }

    private void RegisterUser() {
        String fullname_valid = name.getText().toString().trim();
        String age_valid = age.getText().toString().trim();
        String email_valid = email.getText().toString().trim();
        String password_valid = password.getText().toString().trim();
        String cpassword_valid = verifypassword.getText().toString().trim();
        String role_valid = role.getText().toString().trim();
        List<String> bookings = new ArrayList<String>();


        if(fullname_valid.isEmpty()){
            name.setError("Fullname is required");
            name.requestFocus();
            return;
        }
        if(age_valid.isEmpty()){
            age.setError("Age is required");
            age.requestFocus();
            return;
        }
        if(email_valid.isEmpty()){
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_valid).matches())
        {
            email.setError("Email is invalid");
            email.requestFocus();
            return;
        }

        if(password_valid.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(cpassword_valid.isEmpty()){
            verifypassword.setError("Confirm Password is required");
            verifypassword.requestFocus();
            return;
        }

        if(password_valid.length()<6){
            password.setError("Password should be minimum of 6 characters");
            password.requestFocus();
            return;
        }
        if(cpassword_valid.length()<6){
            verifypassword.setError("Confirm Password should be minimum of 6 characters");
            verifypassword.requestFocus();
            return;
        }
        if(!password_valid.equals(cpassword_valid)){
            verifypassword.setError("Passwords does not match");
            verifypassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email_valid,password_valid)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            int cBookings=0;
                            UserDataHotel user = new UserDataHotel(fullname_valid,age_valid,email_valid,role_valid,cBookings);

                            FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this,"Success",Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(Register.this,"Failed to Register! Try Again!",Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                        }else{
                            Toast.makeText(Register.this,"User Already Exists",Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}