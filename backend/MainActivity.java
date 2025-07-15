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

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView register,login,forgotPass,adminLogin;
    private EditText email,password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register=(TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        login=(TextView) findViewById(R.id.login);
        login.setOnClickListener(this);

        forgotPass=(TextView) findViewById(R.id.forgotPassword);
        forgotPass.setOnClickListener(this);

        email=(EditText) findViewById(R.id.email);
        email.setOnClickListener(this);

        password=(EditText) findViewById(R.id.password);
        password.setOnClickListener(this);

        adminLogin= (TextView) findViewById(R.id.adminLogin);
        adminLogin.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();


    }

    public void onClick(View v)
    {
        switch(v.getId())
        {
            case R.id.register:
                startActivity(new Intent(this,Register.class));
                break;

            case R.id.forgotPassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;

            case R.id.login:
                LoginUser();
                break;

            case R.id.adminLogin:
                startActivity(new Intent(this,AdminLogin.class));
                break;

        }
    }

    private void LoginUser() {
        String email_valid = email.getText().toString().trim();
        String password_valid = password.getText().toString().trim();

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
        mAuth.signInWithEmailAndPassword(email_valid, password_valid).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    startActivity(new Intent(MainActivity.this, Home.class));
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(MainActivity.this, Home.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check email, to verify account", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Incorrect credentials", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }
}