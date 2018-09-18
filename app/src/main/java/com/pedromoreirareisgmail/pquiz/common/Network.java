package com.pedromoreirareisgmail.pquiz.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.Objects;

public class Network {

    public static Boolean hasInternet(Context context){

        boolean hasNet;

        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();

        if(networkInfo != null && networkInfo.isConnected() && networkInfo.isAvailable()){

            hasNet = true;

        }else{

            Toast.makeText(context,"Você não esta conectado a uma rede. Verifique sua conexão…", Toast.LENGTH_SHORT).show();
            hasNet = false;
        }

        return hasNet;
    }
}
