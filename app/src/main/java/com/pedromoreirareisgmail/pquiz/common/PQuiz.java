package com.pedromoreirareisgmail.pquiz.common;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

public class PQuiz extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //  Habilitando Firebase OffLine
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);


        // Picasso Offline
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this,Integer.MAX_VALUE));

        Picasso built = builder.build();

        built.setIndicatorsEnabled(true);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);
    }
}
