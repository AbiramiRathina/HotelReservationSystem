package com.example.hotelbookingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity implements View.OnClickListener{

    private TextView email,reset,login;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();

        reset=(TextView) findViewById(R.id.reset);
        reset.setOnClickListener(this);

        login=(TextView) findViewById(R.id.login);
        login.setOnClickListener(this);

        email=(TextView) findViewById(R.id.email);
        email.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset:
                forgotPassword();
                break;


            case R.id.login:
                startActivity(new Intent(this, MainActivity.class));
                break;

        }

    }

    private void forgotPassword() {
        String email_valid = email.getText().toString().trim();

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

        mAuth.sendPasswordResetEmail(email_valid).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this,"Check email to reset Password",Toast.LENGTH_LONG).show();
                }
                else{
                    Toast.makeText(ForgotPassword.this,"Try Again",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

}