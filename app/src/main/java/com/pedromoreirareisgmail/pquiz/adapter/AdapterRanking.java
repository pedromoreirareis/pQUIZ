package com.pedromoreirareisgmail.pquiz.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.pedromoreirareisgmail.pquiz.R;
import com.pedromoreirareisgmail.pquiz.activitys.ScoreDetailActivity;
import com.pedromoreirareisgmail.pquiz.common.Common;
import com.pedromoreirareisgmail.pquiz.interfaces.ItemClickListener;
import com.pedromoreirareisgmail.pquiz.models.Ranking;
import com.pedromoreirareisgmail.pquiz.viewHolder.RankingViewHolder;

public class AdapterRanking extends FirebaseRecyclerAdapter<Ranking, RankingViewHolder> {


    private Context mContext;
    private ProgressDialog mDialog;

    public AdapterRanking(@NonNull FirebaseRecyclerOptions<Ranking> options, Context context, Activity activity) {
        super(options);
        mContext = context;

        mDialog = new ProgressDialog(mContext);

        //  Cria um layout para Dialog
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.item_dialog, null);

        mDialog.setTitle("Aguarde...");
        mDialog.setMessage("Carregando Ranking...");
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.setView(view);
        mDialog.show();
    }

    @Override
    protected void onBindViewHolder(@NonNull RankingViewHolder holder, int position, @NonNull final Ranking model) {


        holder.rankingUSerName.setText(model.getUserName());
        holder.rankingScore.setText(String.valueOf(model.getScore()));


        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {

                // Click no score de um usuario
                Intent toScoreDetail = new Intent(mContext, ScoreDetailActivity.class);
                toScoreDetail.putExtra(Common.INTENT_USER_NAME,model.getUserName());
                mContext.startActivity(toScoreDetail);

            }
        });

    }

    @NonNull
    @Override
    public RankingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_ranking, parent, false);

        return new RankingViewHolder(view);
    }


    @Override
    public void onDataChanged() {
        super.onDataChanged();

        mDialog.dismiss();
    }
}
