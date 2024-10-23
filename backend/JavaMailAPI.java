package com.example.hotelbookingapp;

import android.content.Context;
import android.os.AsyncTask;
import android.se.omapi.Session;

import java.util.Properties;

public class JavaMailAPI extends AsyncTask<Void,Void,Void> {
    private Context context;
    private Session session;
    private String main, subject, message;

    public JavaMailAPI(Context context,Session session, String main,String subject, String message)
    {
        this.context= context;
        this.session=session;
        this.main=main;
        this.subject=subject;
        this.message=message;
    }

    @Override
    protected Void doInBackground(Void... voids){
        Properties properties= new Properties();
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.socketFactory.port","465");
        properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.auth","true");
        properties.put("mail.smtp.port","465");
    return null;

    }
}
