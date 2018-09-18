package com.pedromoreirareisgmail.pquiz.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.models.Score;
import com.pedromoreirareisgmail.pquiz.viewHolder.ScoreDetailViewHolder;

public class AdapterScoreDetail extends FirebaseRecyclerAdapter<Score, ScoreDetailViewHolder> {

    private Context mContext;
    private ProgressDialog mDialog;

    public AdapterScoreDetail(@NonNull FirebaseRecyclerOptions<Score> options, Context context, Activity activity) {
        super(options);
        mContext = context;

        mDialog = new ProgressDialog(mContext);

        //  Cria um layout para Dialog
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_dialog, null);

        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Carregando pontuações...");
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setView(view);
        mDialog.show();
    }


    @Override
    protected void onBindViewHolder(@NonNull ScoreDetailViewHolder holder, int position, @NonNull Score model) {

        holder.categoryName.setText(model.getCategoryName());
        holder.score.setText(model.getScore());
    }

    @NonNull
    @Override
    public ScoreDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(mContext).inflate(R.layout.item_score_detail, parent, false);

        return new ScoreDetailViewHolder(view);
    }


    @Override
    public void onDataChanged() {
        super.onDataChanged();

        mDialog.dismiss();
    }
}
