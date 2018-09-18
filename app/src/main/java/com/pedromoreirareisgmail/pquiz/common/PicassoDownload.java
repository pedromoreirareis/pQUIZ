package com.pedromoreirareisgmail.pquiz.common;

import android.content.Context;
import android.widget.ImageView;

import com.pedromoreirareisgmail.pquiz.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class PicassoDownload {

    public static void ivChache(final Context context, final String urlImagem, final ImageView ivImagem) {

        Picasso.get()
                .load(urlImagem)
                .fit()
                .centerCrop()
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(ivImagem, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {

                        Picasso.get()
                                .load(urlImagem)
                                .fit()
                                .centerCrop()
                                .placeholder(R.drawable.no_image)
                                .error(R.drawable.no_image)
                                .into(ivImagem);
                    }
                });

    }
}
